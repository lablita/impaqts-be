package it.drwolf.impaqtsbe.utils;

import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.drwolf.impaqtsbe.dto.ErrorResponse;
import it.drwolf.impaqtsbe.dto.QueryRequest;
import it.drwolf.impaqtsbe.dto.QueryResponse;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.libs.Json;
import play.mvc.Http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
	}

	public QueryResponse executeNonQueryRequest(QueryRequest queryRequest) throws IOException {
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
		this.logger.debug(params.stream().collect(Collectors.joining(" ")));
		processBuilder.command(params);
		processBuilder.redirectErrorStream(false);
		Process process = processBuilder.start();
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
			logger.info(paramsEscaped.stream().collect(Collectors.joining(" ")));
		} else {
			this.logger.debug("Query: {}", StringEscapeUtils.escapeJson(Json.stringify(Json.toJson(queryRequest))));
			this.logger.debug("CQL: {}", Json.toJson(queryRequest.getQueryPattern().getCql()));
			params = Arrays.asList(this.javaExecutable, "-jar", this.wrapperPath, "-l", this.manateeLibPath, "-c",
					queryRequest.getCorpus(), "-d", this.cacheDir, "-j", Json.stringify(Json.toJson(queryRequest)));
		}
		logger.info(params.stream().collect(Collectors.joining(" ")));
		processBuilder.command(params);
		processBuilder.redirectErrorStream(false);
		Process process = processBuilder.start();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			ObjectMapper mapper = new ObjectMapper();
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
					QueryResponse qrError = new QueryResponse();
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
			params = Arrays.asList(this.javaExecutable, "-jar", this.wrapperPath, "-l", this.manateeLibPath, "-c",
					queryRequest.getCorpus(), "-d", this.cacheDir, "-j", Json.stringify(Json.toJson(queryRequest)));
		}
		processBuilder.command(params);
		this.logger.debug(params.stream().collect(Collectors.joining(" ")));
		Process process = processBuilder.start();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("###") || line.startsWith("json")) {
					// skip comments line
					continue;
				}
				return Json.parse(line);
			}
		}
		return Json.toJson(null);
	}
}
