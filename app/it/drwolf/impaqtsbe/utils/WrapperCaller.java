package it.drwolf.impaqtsbe.utils;

import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.drwolf.impaqtsbe.dto.ErrorResponse;
import it.drwolf.impaqtsbe.dto.QueryRequest;
import it.drwolf.impaqtsbe.dto.QueryResponse;
import it.drwolf.impaqtsbe.services.ExportCsvService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.libs.Json;
import play.mvc.Http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WrapperCaller {

	public static final String MANATEE_REGISTRY = "MANATEE_REGISTRY";
	public static final String USR_LOCAL_BIN_DOCKER = "/usr/local/bin/docker";
	public static final String MANATEE = "manatee";
	public static final String NAME_PARAM = "--name";
	private static final int MAX_ITEMS = 50000;
	private static final String ERROR_PREFIX = "ERROR";
	private final ActorRef out;
	private final String manateeRegistryPath;
	private final String manateeLibPath;
	private final String javaExecutable;
	private final String wrapperPath;
	private final String dockerSwitch;
	private final String dockerManateeRegistry;
	private final String dockerManateePath;
	private final String cacheDir;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final Integer csvMaxLength = 10000;
	private final String frequencyListsDir;
	private String csvExt = "";
	private String csvTempPath = "";
	private String progressFileCsv = "";

	public WrapperCaller(ActorRef out, String manateeRegistryPath, String manateeLibPath, String javaExecutable,
			String wrapperPath, String dockerSwitch, String dockerManateeRegistry, String dockerManateePath,
			String cacheDir) {
		this.out = out;
		this.manateeRegistryPath = manateeRegistryPath;
		this.manateeLibPath = manateeLibPath;
		this.javaExecutable = javaExecutable;
		this.wrapperPath = wrapperPath;
		this.dockerSwitch = dockerSwitch;
		this.dockerManateeRegistry = dockerManateeRegistry;
		this.dockerManateePath = dockerManateePath;
		this.cacheDir = cacheDir;
		this.frequencyListsDir = null;
	}

	public WrapperCaller(ActorRef out, String manateeRegistryPath, String manateeLibPath, String javaExecutable,
			String wrapperPath, String dockerSwitch, String dockerManateeRegistry, String dockerManateePath,
			String cacheDir, String csvExt, String csvTempPath, String progressFileCsv, String frequencyListsDir) {
		this.out = out;
		this.manateeRegistryPath = manateeRegistryPath;
		this.manateeLibPath = manateeLibPath;
		this.javaExecutable = javaExecutable;
		this.wrapperPath = wrapperPath;
		this.dockerSwitch = dockerSwitch;
		this.dockerManateeRegistry = dockerManateeRegistry;
		this.dockerManateePath = dockerManateePath;
		this.cacheDir = cacheDir;
		this.csvExt = csvExt;
		this.csvTempPath = csvTempPath;
		this.progressFileCsv = progressFileCsv;
		this.frequencyListsDir = frequencyListsDir;
	}

	public QueryResponse executeNonQueryRequest(QueryRequest queryRequest) throws IOException {
		Process process = getProcess(queryRequest);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				this.logger.debug("Result line: {}", line);
				if (line.startsWith("###") || line.startsWith("json") || line.startsWith("***")) {
					// skip comments line
					continue;
				}
				JsonNode lineJson = null;
				try {
					lineJson = Json.parse(line);
				} catch (RuntimeException re) {
					this.logger.error(String.format("Parse error line %s", line));
					continue;
				}
				return Json.fromJson(lineJson, QueryResponse.class);
			}
		}
		return null;
	}

	public void executeQuery(QueryRequest queryRequest) throws IOException {
		int start = queryRequest.getStart();
		int end = queryRequest.getEnd();
		if (queryRequest.getCollocationQueryRequest() != null) {
			queryRequest.setStart(0);
			queryRequest.setEnd(WrapperCaller.MAX_ITEMS);
		}
		Process process = this.getProcess(queryRequest);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			ObjectMapper mapper = new ObjectMapper();
			String line;
			while ((line = reader.readLine()) != null) {
				this.logger.debug("Result line: {}", line);
				if (line.startsWith("*** ERROR ")) {
					// gestione degli errori di sintassi CQL
					QueryResponse qrError = new QueryResponse(queryRequest.getId());
					ErrorResponse er = new ErrorResponse();
					er.setErrorCode(Http.Status.BAD_REQUEST);
					er.setErrorMessage(line.trim());
					qrError.setErrorResponse(er);
					this.out.tell(Json.toJson(qrError), null);
					this.logger.error(line.trim());
					continue;
				}
				if (line.startsWith("###") || line.startsWith("json") || line.startsWith("***")) {
					// skip comments line
					continue;
				}
				JsonNode lineJson = null;
				try {
					lineJson = Json.parse(line);
				} catch (RuntimeException re) {
					QueryResponse qrError = new QueryResponse(queryRequest.getId());
					ErrorResponse er = new ErrorResponse();
					er.setErrorCode(Http.Status.INTERNAL_SERVER_ERROR);
					er.setErrorMessage(String.format("%s Parse error line %s", ERROR_PREFIX, line));
					qrError.setErrorResponse(er);
					this.out.tell(Json.toJson(qrError), null);
					this.logger.error(String.format("Parse error line %s", line));
					continue;
				}
				if (queryRequest.getCollocationQueryRequest() != null) {
					//pagination collocations
					ArrayNode newArrayNode = mapper.createArrayNode();
					ArrayNode arrayNode = (ArrayNode) lineJson.get("collocations");
					for (int index = start; index < end; index++) {
						newArrayNode.add(arrayNode.get(index));
					}
					((ObjectNode) lineJson).replace("collocations", newArrayNode);
				}
				this.out.tell(lineJson, null);
			}
		}
	}

	public void executeQueryAndWriteCSV(QueryRequest queryRequest, ExportCsvService exportCsvService,
			QueryRequest.RequestType queryType, String uuid, boolean complete) throws Exception {
		String tmpPathStr = this.csvTempPath + "/" + uuid;
		Files.createDirectories(Paths.get(tmpPathStr));
		final String filePathStr = tmpPathStr + "/" + uuid + this.csvExt;
		final String progressFilePathStr = tmpPathStr + "/" + this.progressFileCsv;
		QueryResponse queryResponse = this.executeNonQueryRequest(queryRequest);
		Integer resultSize;
		if (QueryRequest.RequestType.METADATA_FREQUENCY_QUERY_REQUEST.toString().equals(queryRequest.getQueryType())
				|| QueryRequest.RequestType.MULTI_FREQUENCY_QUERY_REQUEST.toString()
				.equals(queryRequest.getQueryType())) {
			resultSize = queryResponse.getFrequency().getTotal();
		} else if (QueryRequest.RequestType.WORD_LIST_REQUEST.toString().equals(queryRequest.getQueryType())) {
			resultSize = queryResponse.getWordList().getTotalItems();
		} else {
			resultSize = queryResponse.getCurrentSize();
		}
		if (!complete) {
			//max 10000 lines
			resultSize = resultSize <= csvMaxLength ? resultSize : csvMaxLength;
		}
		Integer pageSize = queryRequest.getEnd() - queryRequest.getStart();
		Integer start;
		Integer end = queryRequest.getEnd();
		exportCsvService.storageTmpFileCsvFromQueryResponse(queryResponse, queryType, filePathStr, true);
		boolean completeWithoutErrors = true;
		final File progressFile = new File(progressFilePathStr);
		if (end >= resultSize) {
			FileUtils.writeStringToFile(progressFile, "OK", StandardCharsets.UTF_8);
		} else {
			while (end < resultSize) {
				start = end;
				queryRequest.setStart(start);
				end += pageSize;
				queryRequest.setEnd(end);
				queryResponse = this.executeNonQueryRequest(queryRequest);
				try {
					exportCsvService.storageTmpFileCsvFromQueryResponse(queryResponse, queryType, filePathStr, false);
				} catch (Exception e) {
					completeWithoutErrors = false;
					FileUtils.writeStringToFile(progressFile, "KO", StandardCharsets.UTF_8);
					break;
				}
				int progress = this.progressValue(resultSize, end);
				FileUtils.writeStringToFile(progressFile, (end < resultSize ? String.valueOf(progress) : "OK"),
						StandardCharsets.UTF_8);
			}

		}
		if (complete && completeWithoutErrors) {
			// move generated file to frequencyListsFolder
			Path frequencyListPath = Files.createDirectories(
					Paths.get(this.frequencyListsDir, queryRequest.getCorpus()));
			String fileName = "all_lemmas.csv";
			if ("word".equals(queryRequest.getWordListRequest().getSearchAttribute())) {
				fileName = "all_words.csv";
			}
			if (frequencyListPath != null && Files.exists(frequencyListPath) && Files.isWritable(frequencyListPath)) {
				Path destFilePath = Paths.get(frequencyListPath.toString(), fileName);
				Files.move(Paths.get(filePathStr), destFilePath, StandardCopyOption.REPLACE_EXISTING);
			} else {
				logger.error("Unable to write frequency lists");
			}
		}
	}

	private Process getProcess(QueryRequest queryRequest) throws IOException {
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.environment().put(MANATEE_REGISTRY, this.manateeRegistryPath);
		List<String> params;
		if (this.dockerSwitch.equals("yes")) {
			params = Arrays.asList(USR_LOCAL_BIN_DOCKER, "run", "-e", this.dockerManateeRegistry, "-v",
					this.dockerManateePath, "--rm", NAME_PARAM, MANATEE, MANATEE, "java", "-jar", this.wrapperPath,
					"-l", this.manateeLibPath, "-c", queryRequest.getCorpus(), "-d", this.cacheDir, "-j",
					Json.stringify(Json.toJson(queryRequest)));
			List<String> paramsEscaped = Arrays.asList(USR_LOCAL_BIN_DOCKER, "run", "-e", this.dockerManateeRegistry,
					"-v", this.dockerManateePath, "--rm", NAME_PARAM, MANATEE, MANATEE, "java", "-jar",
					this.wrapperPath, "-l", this.manateeLibPath, "-c", queryRequest.getCorpus(), "-d", this.cacheDir,
					"-j", "\"" + StringEscapeUtils.escapeJson(Json.stringify(Json.toJson(queryRequest))) + "\"");
			this.logger.debug(paramsEscaped.stream().collect(Collectors.joining(" ")));
		} else {
			this.logger.debug("Query: {}", StringEscapeUtils.escapeJson(Json.stringify(Json.toJson(queryRequest))));
			if (queryRequest.getQueryPattern() != null) {
				this.logger.debug("CQL: {}", Json.toJson(queryRequest.getQueryPattern().getCql()));
			}
			params = Arrays.asList(this.javaExecutable, "-jar", this.wrapperPath, "-l", this.manateeLibPath, "-c",
					queryRequest.getCorpus(), "-d", this.cacheDir, "-j", Json.stringify(Json.toJson(queryRequest)));
		}
		this.logger.debug(params.stream().collect(Collectors.joining(" ")));
		processBuilder.command(params);
		processBuilder.redirectErrorStream(false);
		Process process = processBuilder.start();
		return process;
	}

	private int progressValue(Integer resultSize, Integer end) {
		float percent = (Float.valueOf(end) / Float.valueOf(resultSize)) * 100;
		return (int) percent;
	}

	public JsonNode retrieveMetadatumValues(QueryRequest queryRequest) throws IOException {
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.environment().put(MANATEE_REGISTRY, this.manateeRegistryPath);
		List<String> params;
		if (this.dockerSwitch.equals("yes")) {
			params = Arrays.asList(USR_LOCAL_BIN_DOCKER, "run", "-e", this.dockerManateeRegistry, "-v",
					this.dockerManateePath, "--rm", NAME_PARAM, MANATEE, MANATEE, "java", "-jar", this.wrapperPath,
					"-l", this.manateeLibPath, "-c", queryRequest.getCorpus(), "-d", this.cacheDir, "-j",
					Json.stringify(Json.toJson(queryRequest)));
			List<String> paramsEscaped = Arrays.asList(USR_LOCAL_BIN_DOCKER, "run", "-e", this.dockerManateeRegistry,
					"-v", this.dockerManateePath, "--rm", NAME_PARAM, MANATEE, MANATEE, "java", "-jar",
					this.wrapperPath, "-l", this.manateeLibPath, "-c", queryRequest.getCorpus(), "-d", this.cacheDir,
					"-j", "\"" + StringEscapeUtils.escapeJson(Json.stringify(Json.toJson(queryRequest))) + "\"");
			this.logger.debug(paramsEscaped.stream().collect(Collectors.joining(" ")));
		} else {
			this.logger.debug("Query: {}", StringEscapeUtils.escapeJson(Json.stringify(Json.toJson(queryRequest))));
			params = Arrays.asList(this.javaExecutable, "-jar", this.wrapperPath, "-l", this.manateeLibPath, "-c",
					queryRequest.getCorpus(), "-d", this.cacheDir, "-j", Json.stringify(Json.toJson(queryRequest)));
		}
		processBuilder.command(params);
		this.logger.debug(params.stream().collect(Collectors.joining(" ")));
		Process process = processBuilder.start();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("###") || line.startsWith("json") || line.startsWith("***")) {
					// skip comments line
					continue;
				}
				return Json.parse(line);
			}
		}
		return Json.toJson(null);
	}
}
