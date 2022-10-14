package it.drwolf.impaqtsbe.controllers;

import it.drwolf.impaqtsbe.startup.Startup;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CorpusController extends Controller {

	private static final String APPLICATION_GZIP = "application/gzip";
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
			final String notAFolderMessage = String.format("%s exists but is not a folder.", corpusName);
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

	private void extractZIPCorpus(Path compressedCorpus) throws ZipException {
		new ZipFile(compressedCorpus.toString()).extractAll(compressedCorpus.getParent().toString());
	}

	public Result uploadCorpus(Http.Request request) {
		final String corporaFolderPath = startup.getCorporaFolderPath();
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
			final String notAFolderMessage = String.format("%s exists but is not a folder.", corporaFolderPath);
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
		String extractedCorpusFolder = null;
		if (contentType != null) {
			try {
				if (APPLICATION_ZIP.equals(contentType)) {
					this.extractZIPCorpus(compressedCorpus);
				} else {
					final String contentTypeNotSupported = String.format("%s content type is not supported.",
							compressedCorpus);
					return Results.badRequest(contentTypeNotSupported);
				}
			} catch (ZipException e) {
				final String errorExtractingDataMessage = String.format("Error while extracting compressed file %s.",
						compressedCorpus);
				return Results.badRequest(errorExtractingDataMessage);
			}
		} else {
			final String contentTypeNotRetrievedMessage = String.format("Cannot retrieve content type of %s.",
					compressedCorpus);
			return Results.internalServerError(contentTypeNotRetrievedMessage);
		}
		compressedCorpus.toFile().delete();
		final String okMessage = String.format(this.startup.getCorporaFolderPath());
		return Results.ok(okMessage);
	}

	public Result uploadRegistry(Http.Request request) {
		final String registryFolderPath = startup.getManateeRegistryPath();
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
			final String notAFolderMessage = String.format("%s exists but is not a folder.", registryContainerFolder);
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
