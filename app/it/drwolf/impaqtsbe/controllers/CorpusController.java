package it.drwolf.impaqtsbe.controllers;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.fasterxml.jackson.databind.JsonNode;
import it.drwolf.impaqtsbe.actors.CorpusUnzipperActor;
import it.drwolf.impaqtsbe.actors.ExportCsvActor;
import it.drwolf.impaqtsbe.actors.messages.CorpusUnzipperMessage;
import it.drwolf.impaqtsbe.actors.messages.ExportCsvMessage;
import it.drwolf.impaqtsbe.dto.QueryRequest;
import it.drwolf.impaqtsbe.dto.QueryResponse;
import it.drwolf.impaqtsbe.dto.WordListRequest;
import it.drwolf.impaqtsbe.services.ExportCsvService;
import it.drwolf.impaqtsbe.startup.Startup;
import it.drwolf.impaqtsbe.utils.WrapperCaller;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CorpusController extends Controller {
	public static final String EXISTS_BUT_IS_NOT_A_FOLDER = "%s exists but is not a folder.";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final Startup startup;
	private final ActorRef unzipperActor;
	private final ActorRef exportCsvActor;
	private final ExportCsvService exportCsvService;

	@Inject
	public CorpusController(Startup startup, ActorSystem actorSystem, ExportCsvService exportCsvService) {
		this.startup = startup;
		this.unzipperActor = actorSystem.actorOf(CorpusUnzipperActor.getProps());
		this.exportCsvActor = actorSystem.actorOf(ExportCsvActor.getProps());
		this.exportCsvService = exportCsvService;
	}

	public Result corpusUploadStatus(final String uuid) {
		String tmpDir = System.getProperty("java.io.tmpdir");
		Path tmp = FileSystems.getDefault().getPath(tmpDir, uuid + ".session");
		File tmpFile = tmp.toFile();
		if (!tmpFile.exists()) {
			return Results.notFound(String.format("Il file di stato non esiste: %s", uuid));
		}
		if (!tmpFile.canRead()) {
			return Results.notFound(String.format("Il file di stato non è leggibile: %s", uuid));
		}
		try {
			List<String> lines = Files.readAllLines(tmp, StandardCharsets.UTF_8);
			String output = lines.stream().collect(Collectors.joining("\n"));
			return Results.ok(output);
		} catch (IOException e) {
			final String readError = String.format("Error reading file %s", uuid);
			this.logger.error(readError);
			return Results.internalServerError(readError);
		}
	}

	public Result deleteCorpus(String corpusName) {
		File corpusFolder = Paths.get(this.startup.getCorporaFolderPath(), corpusName).toFile();
		if (!corpusFolder.exists()) {
			final String folderNotExistsMessage = String.format("Unable to locate %s folder.", corpusName);
			return Results.badRequest(folderNotExistsMessage);
		}
		// file exists: check if it's a folder
		if (!corpusFolder.isDirectory()) {
			final String notAFolderMessage = String.format(CorpusController.EXISTS_BUT_IS_NOT_A_FOLDER, corpusName);
			return Results.internalServerError(notAFolderMessage);
		}
		// folder exists: check if it's writable
		if (!corpusFolder.canWrite()) {
			final String notWritableFolderMessage = String.format("Cannot delete folder %s.", corpusName);
			return Results.internalServerError(notWritableFolderMessage);
		}
		try {
			FileUtils.deleteDirectory(corpusFolder);
		} catch (IOException e) {
			final String errorDeletingMessage = String.format("Error while deleting folder %s.", corpusName);
			return Results.internalServerError(errorDeletingMessage);
		}
		return Results.noContent();
	}

	public Result deleteRegistry(String registryName) {
		File registry = Paths.get(this.startup.getManateeRegistryPath(), registryName).toFile();
		if (!registry.exists()) {
			final String fileNotExistsMessage = String.format("Unable to locate %s file.", registryName);
			return Results.badRequest(fileNotExistsMessage);
		}
		// folder exists: check if it's writable
		if (!registry.canWrite()) {
			final String notWritableFolderMessage = String.format("Cannot delete file %s.", registryName);
			return Results.internalServerError(notWritableFolderMessage);
		}
		try {
			FileUtils.delete(registry);
		} catch (IOException e) {
			final String errorDeletingMessage = String.format("Error while deleting file %s.", registryName);
			return Results.internalServerError(errorDeletingMessage);
		}
		return Results.noContent();
	}

	public Result downloadList(String corpusName, String listType) {
		String fn = "all_lemmas.csv";
		if (listType != null && listType.equals("word")) {
			fn = "all_words.csv";
		}
		Path fileToDownload = Paths.get(this.startup.getFrequencyListsDir(), corpusName, fn);
		if (!Files.exists(fileToDownload) || !Files.isReadable(fileToDownload)) {
			return Results.notFound();
		}
		return Results.ok(fileToDownload.toFile())
				.as("text/csv")
				.withHeader(Http.HeaderNames.CONTENT_DISPOSITION, "attachment; filename=" + fn)
				.withHeader("Download-Filename", fn);
	}

	public Result extractBasicFrequencyLists(Http.Request request, String corpusName) {
		WrapperCaller wrapperCaller = new WrapperCaller(null, this.startup.getManateeRegistryPath(),
				this.startup.getManateeLibPath(), this.startup.getJavaExecutable(), this.startup.getWrapperPath(),
				this.startup.getDockerSwitch(), this.startup.getDockerManateeRegistry(),
				this.startup.getDockerManateePath(), this.startup.getCacheDir(), this.startup.getCsvExt(),
				this.startup.getCsvTempPath(), this.startup.getProgressFileCsv(), this.startup.getFrequencyListsDir());
		QueryRequest queryRequest = new QueryRequest();
		queryRequest.setStart(0);
		queryRequest.setEnd(Integer.MAX_VALUE);
		queryRequest.setCorpus(corpusName);
		queryRequest.setCql("");
		queryRequest.setQueryType("WORD_LIST_REQUEST");
		WordListRequest wordListRequest = new WordListRequest();
		wordListRequest.setSearchAttribute("lemma");
		wordListRequest.setSortField("freq");
		wordListRequest.setSortDir("desc");
		queryRequest.setWordListRequest(wordListRequest);
		QueryRequest.RequestType queryType = QueryRequest.RequestType.valueOf(queryRequest.getQueryType());
		ExportCsvMessage exportCsvMessage = new ExportCsvMessage();
		exportCsvMessage.setWrapperCaller(wrapperCaller);
		exportCsvMessage.setExportCsvService(this.exportCsvService);
		exportCsvMessage.setQueryRequest(queryRequest);
		exportCsvMessage.setQueryType(queryType);
		exportCsvMessage.setUuid(UUID.randomUUID().toString());
		exportCsvMessage.setComplete(true);
		exportCsvActor.tell(exportCsvMessage, null);

		queryRequest = new QueryRequest();
		queryRequest.setStart(0);
		queryRequest.setEnd(Integer.MAX_VALUE);
		queryRequest.setCorpus(corpusName);
		queryRequest.setCql("");
		queryRequest.setQueryType("WORD_LIST_REQUEST");
		wordListRequest = new WordListRequest();
		wordListRequest.setSearchAttribute("word");
		wordListRequest.setSortField("freq");
		wordListRequest.setSortDir("desc");
		queryRequest.setWordListRequest(wordListRequest);
		queryType = QueryRequest.RequestType.valueOf(queryRequest.getQueryType());
		exportCsvMessage = new ExportCsvMessage();
		exportCsvMessage.setWrapperCaller(wrapperCaller);
		exportCsvMessage.setExportCsvService(this.exportCsvService);
		exportCsvMessage.setQueryRequest(queryRequest);
		exportCsvMessage.setQueryType(queryType);
		exportCsvMessage.setUuid(UUID.randomUUID().toString());
		exportCsvMessage.setComplete(true);
		exportCsvActor.tell(exportCsvMessage, null);
		return Results.ok(Json.toJson("Creazione liste di frequenza iniziata"));
	}

	public Result getCorpusInfo(String corpusName) {
		QueryRequest qr = new QueryRequest();
		qr.setCorpus(corpusName);
		qr.setQueryType("CORPUS_INFO");
		QueryResponse queryResponse = null;
		WrapperCaller wrapperCaller = new WrapperCaller(null, this.startup.getManateeRegistryPath(),
				this.startup.getManateeLibPath(), this.startup.getJavaExecutable(), this.startup.getWrapperPath(),
				this.startup.getDockerSwitch(), this.startup.getDockerManateeRegistry(),
				this.startup.getDockerManateePath(), this.startup.getCacheDir());
		try {
			queryResponse = wrapperCaller.executeNonQueryRequest(qr);
		} catch (IOException e) {
			final String corpusInfoErrorMessage = String.format("Error while retrieving info for corpus %s",
					corpusName);
			return Results.internalServerError(corpusInfoErrorMessage);
		}
		return Results.ok(Json.toJson(queryResponse));
	}

	public Result getWideContext(String corpusName, Long pos, Integer hitlen) {
		QueryRequest qr = new QueryRequest();
		qr.getWideContextRequest().setCorpusName(corpusName);
		qr.getWideContextRequest().setPos(pos);
		qr.getWideContextRequest().setHitlen(hitlen);
		qr.setCorpus(corpusName);
		qr.setQueryType("WIDE_CONTEXT_QUERY_REQUEST");
		QueryResponse queryResponse = null;
		WrapperCaller wrapperCaller = new WrapperCaller(null, this.startup.getManateeRegistryPath(),
				this.startup.getManateeLibPath(), this.startup.getJavaExecutable(), this.startup.getWrapperPath(),
				this.startup.getDockerSwitch(), this.startup.getDockerManateeRegistry(),
				this.startup.getDockerManateePath(), this.startup.getCacheDir());
		try {
			queryResponse = wrapperCaller.executeNonQueryRequest(qr);
		} catch (IOException e) {
			final String wideContextRetrievalErrorMessage = String.format("Error while retrieving context %s %d %d",
					corpusName, pos, hitlen);
			return Results.internalServerError(wideContextRetrievalErrorMessage);
		}
		return Results.ok(Json.toJson(queryResponse));
	}

	public Result getWordList(Http.Request request) {
		JsonNode bodyAsJson = request.body().asJson();
		QueryRequest qr = Json.fromJson(bodyAsJson, QueryRequest.class);
		QueryResponse queryResponse = null;
		WrapperCaller wrapperCaller = new WrapperCaller(null, this.startup.getManateeRegistryPath(),
				this.startup.getManateeLibPath(), this.startup.getJavaExecutable(), this.startup.getWrapperPath(),
				this.startup.getDockerSwitch(), this.startup.getDockerManateeRegistry(),
				this.startup.getDockerManateePath(), this.startup.getCacheDir());
		try {
			queryResponse = wrapperCaller.executeNonQueryRequest(qr);
		} catch (IOException e) {
			final String corpusInfoErrorMessage = String.format("Error while word list for corpus %s", qr.getCorpus());
			return Results.internalServerError(corpusInfoErrorMessage);
		}
		return Results.ok(Json.toJson(queryResponse));
	}

	public Result uploadCorpus(Http.Request request) {
		final UUID uploadSessionUUID = UUID.randomUUID();
		Http.MultipartFormData<play.libs.Files.TemporaryFile> compressedCorpusMultipartFormData = request.body()
				.asMultipartFormData();
		List<Http.MultipartFormData.FilePart<play.libs.Files.TemporaryFile>> compressedCorpusFileParts = compressedCorpusMultipartFormData.getFiles();
		// file has arrived
		final CorpusUnzipperMessage message = new CorpusUnzipperMessage();
		message.setSessionUUID(uploadSessionUUID);
		message.setCorporaFolderPath(this.startup.getCorporaFolderPath());
		message.setCompressedCorpusFileParts(compressedCorpusFileParts);
		try {
			Path sessionFile = Files.createTempFile(message.getSessionUUID().toString(), ".session");
			message.setSessionFile(sessionFile);
			this.unzipperActor.tell(message, null);
			return Results.ok(FilenameUtils.getBaseName(sessionFile.getFileName().toString()));
		} catch (IOException e) {
			String sessionFileError = "Errore nella creazione del file di sessione";
			return Results.internalServerError(sessionFileError);
		}
	}

	public Result uploadRegistry(Http.Request request) {
		final String registryFolderPath = this.startup.getManateeRegistryPath();
		File registryContainerFolder = new File(registryFolderPath);
		if (!registryContainerFolder.exists()) {
			// try to create folder
			boolean corpusContainerFolderCreated = registryContainerFolder.mkdirs();
			if (!corpusContainerFolderCreated) {
				final String folderNotCreatedMessage = String.format("Unable to create %s folder.",
						registryContainerFolder);
				return Results.internalServerError(folderNotCreatedMessage);
			}
		}
		// file exists: check if it's a folder
		if (!registryContainerFolder.isDirectory()) {
			final String notAFolderMessage = String.format(CorpusController.EXISTS_BUT_IS_NOT_A_FOLDER,
					registryContainerFolder);
			return Results.internalServerError(notAFolderMessage);
		}
		// folder exists: check if it's writable
		if (!registryContainerFolder.canRead() || !registryContainerFolder.canWrite()) {
			final String notWritableFolderMessage = String.format("Cannot write into %s.", registryContainerFolder);
			return Results.internalServerError(notWritableFolderMessage);
		}
		Http.MultipartFormData<play.libs.Files.TemporaryFile> registryMultipartFormData = request.body()
				.asMultipartFormData();
		List<Http.MultipartFormData.FilePart<play.libs.Files.TemporaryFile>> registryFileParts = registryMultipartFormData.getFiles();
		if (registryFileParts == null || registryFileParts.isEmpty()) {
			final String noFileUploadedMessage = "No file has been uploaded.";
			return Results.badRequest(noFileUploadedMessage);
		}
		Http.MultipartFormData.FilePart<play.libs.Files.TemporaryFile> registryFilePart = registryFileParts.get(0);
		String fileName = registryFilePart.getFilename();
		play.libs.Files.TemporaryFile tempRegistry = registryFilePart.getRef();
		// never overwrite, use deleteRegistry instead
		Path registry = tempRegistry.copyTo(Paths.get(registryFolderPath, fileName), false);
		final String okMessage = String.format("Registry file %s successfully uploaded", registry.toString());
		return Results.ok(okMessage);
	}
}
