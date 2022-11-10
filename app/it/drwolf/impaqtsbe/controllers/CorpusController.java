package it.drwolf.impaqtsbe.controllers;

import it.drwolf.impaqtsbe.dto.QueryRequest;
import it.drwolf.impaqtsbe.dto.QueryResponse;
import it.drwolf.impaqtsbe.startup.Startup;
import it.drwolf.impaqtsbe.utils.WrapperCaller;
import net.lingala.zip4j.ZipFile;
import org.apache.commons.io.FileUtils;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CorpusController extends Controller {

	public static final String EXISTS_BUT_IS_NOT_A_FOLDER = "%s exists but is not a folder.";
	private static final String APPLICATION_ZIP = "application/zip";
	private final Startup startup;

	@Inject
	public CorpusController(Startup startup) {
		this.startup = startup;
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

	private void extractZIPCorpus(Path compressedCorpus) throws IOException {
		try (ZipFile zipFile = new ZipFile(compressedCorpus.toString())) {
			zipFile.extractAll(compressedCorpus.getParent().toString());
		}
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
			queryResponse = wrapperCaller.executeWideContextQuery(qr);
		} catch (IOException e) {
			final String wideContextRetrievalErrorMessage = String.format("Error while retrieving context %s %d %d",
					corpusName, pos, hitlen);
			return Results.internalServerError(wideContextRetrievalErrorMessage);
		}
		return Results.ok(Json.toJson(queryResponse));
	}

	public Result uploadCorpus(Http.Request request) {
		final String corporaFolderPath = this.startup.getCorporaFolderPath();
		File corpusContainerFolder = new File(corporaFolderPath);
		if (!corpusContainerFolder.exists()) {
			// try to create folder
			boolean corpusContainerFolderCreated = corpusContainerFolder.mkdirs();
			if (!corpusContainerFolderCreated) {
				final String folderNotCreatedMessage = String.format("Unable to create %s folder.", corporaFolderPath);
				return Results.internalServerError(folderNotCreatedMessage);
			}
		}
		// file exists: check if it's a folder
		if (!corpusContainerFolder.isDirectory()) {
			final String notAFolderMessage = String.format(CorpusController.EXISTS_BUT_IS_NOT_A_FOLDER,
					corporaFolderPath);
			return Results.internalServerError(notAFolderMessage);
		}
		// folder exists: check if it's writable
		if (!corpusContainerFolder.canRead() || !corpusContainerFolder.canWrite()) {
			final String notWritableFolderMessage = String.format("Cannot write into %s.", corporaFolderPath);
			return Results.internalServerError(notWritableFolderMessage);
		}
		Http.MultipartFormData<play.libs.Files.TemporaryFile> compressedCorpusMultipartFormData = request.body()
				.asMultipartFormData();
		List<Http.MultipartFormData.FilePart<play.libs.Files.TemporaryFile>> compressedCorpusFileParts = compressedCorpusMultipartFormData.getFiles();
		if (compressedCorpusFileParts == null || compressedCorpusFileParts.isEmpty()) {
			final String noFileUploadedMessage = "No file has been uploaded.";
			return Results.badRequest(noFileUploadedMessage);
		}
		Http.MultipartFormData.FilePart<play.libs.Files.TemporaryFile> compressedCorpusFilePart = compressedCorpusFileParts.get(
				0);
		String fileName = compressedCorpusFilePart.getFilename();
		play.libs.Files.TemporaryFile tempCompressedCorpus = compressedCorpusFilePart.getRef();
		// never overwrite, use deleteCorpus instead
		Path compressedCorpus = tempCompressedCorpus.copyTo(
				Paths.get(corpusContainerFolder.getAbsolutePath(), fileName), false);
		URLConnection urlConnection = null;
		try {
			urlConnection = compressedCorpus.toFile().toURI().toURL().openConnection();
		} catch (IOException e) {
			final String contentTypeNotRetrievedMessage = String.format("Cannot retrieve content type of %s.",
					compressedCorpus);
			return Results.internalServerError(contentTypeNotRetrievedMessage);
		}
		String contentType = urlConnection.getContentType();
		if (contentType != null) {
			try {
				if (CorpusController.APPLICATION_ZIP.equals(contentType)) {
					this.extractZIPCorpus(compressedCorpus);
				} else {
					final String contentTypeNotSupported = String.format("%s content type is not supported.",
							compressedCorpus);
					return Results.badRequest(contentTypeNotSupported);
				}
			} catch (IOException e) {
				final String errorExtractingDataMessage = String.format("Error while extracting compressed file %s.",
						compressedCorpus);
				return Results.badRequest(errorExtractingDataMessage);
			}
		} else {
			final String contentTypeNotRetrievedMessage = String.format("Cannot retrieve content type of %s.",
					compressedCorpus);
			return Results.internalServerError(contentTypeNotRetrievedMessage);
		}
		try {
			Files.deleteIfExists(compressedCorpus);
		} catch (IOException e) {
			final String compressedCorpusNotDeletable = String.format(
					"Cannot delete compressed corpus after upload: %s.", compressedCorpus);
			return Results.internalServerError(compressedCorpusNotDeletable);
		}

		final String okMessage = String.format(this.startup.getCorporaFolderPath());
		return Results.ok(okMessage);
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
