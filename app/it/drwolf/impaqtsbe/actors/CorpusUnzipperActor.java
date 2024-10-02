/*
 * Copyright (C) 2024
 * EMMACorpus
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.drwolf.impaqtsbe.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import it.drwolf.impaqtsbe.actors.messages.CorpusUnzipperMessage;
import it.drwolf.impaqtsbe.controllers.CorpusController;
import net.lingala.zip4j.ZipFile;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.Http;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class CorpusUnzipperActor extends AbstractActor {
	private static final String APPLICATION_ZIP = "application/zip";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static Props getProps() {
		return Props.create(CorpusUnzipperActor.class);
	}

	@Override
	public Receive createReceive() {
		return this.receiveBuilder().match(CorpusUnzipperMessage.class, message -> {
			this.unzipCorpus(message);
		}).build();
	}

	private void extractZIPCorpus(Path compressedCorpus) throws IOException {
		try (ZipFile zipFile = new ZipFile(compressedCorpus.toString())) {
			zipFile.extractAll(compressedCorpus.getParent().toString());
		}
	}

	private void unzipCorpus(CorpusUnzipperMessage message) throws IOException {
		final String corporaFolderPath = message.getCorporaFolderPath();
		File corpusContainerFolder = new File(corporaFolderPath);
		File sessionFile = message.getSessionFile().toFile();
		if (!corpusContainerFolder.exists()) {
			// try to create folder
			boolean corpusContainerFolderCreated = corpusContainerFolder.mkdirs();
			if (!corpusContainerFolderCreated) {
				final String folderNotCreatedMessage = String.format("Unable to create %s folder.", corporaFolderPath);
				this.logger.error(folderNotCreatedMessage);
				FileUtils.writeStringToFile(sessionFile, folderNotCreatedMessage);

			}
		}
		// file exists: check if it's a folder
		if (!corpusContainerFolder.isDirectory()) {
			final String notAFolderMessage = String.format(CorpusController.EXISTS_BUT_IS_NOT_A_FOLDER,
					corporaFolderPath);
			this.logger.error(notAFolderMessage);
			FileUtils.writeStringToFile(sessionFile, notAFolderMessage);
		}
		// folder exists: check if it's writable
		if (!corpusContainerFolder.canRead() || !corpusContainerFolder.canWrite()) {
			final String notWritableFolderMessage = String.format("Cannot write into %s.", corporaFolderPath);
			this.logger.error(notWritableFolderMessage);
			FileUtils.writeStringToFile(sessionFile, notWritableFolderMessage);
		}
		if (message.getCompressedCorpusFileParts() == null || message.getCompressedCorpusFileParts().isEmpty()) {
			final String noFileUploadedMessage = "No file has been uploaded.";
			this.logger.error(noFileUploadedMessage);
			FileUtils.writeStringToFile(sessionFile, noFileUploadedMessage);
		}
		Http.MultipartFormData.FilePart<play.libs.Files.TemporaryFile> compressedCorpusFilePart = message.getCompressedCorpusFileParts()
				.get(0);
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
			this.logger.error(contentTypeNotRetrievedMessage);
			FileUtils.writeStringToFile(sessionFile, contentTypeNotRetrievedMessage);
		}
		String contentType = urlConnection.getContentType();
		if (contentType != null) {
			try {
				if (APPLICATION_ZIP.equals(contentType)) {
					this.extractZIPCorpus(compressedCorpus);
				} else {
					final String contentTypeNotSupported = String.format("%s content type is not supported.",
							compressedCorpus);
					this.logger.error(contentTypeNotSupported);
					FileUtils.writeStringToFile(sessionFile, contentTypeNotSupported);
				}
			} catch (IOException e) {
				final String errorExtractingDataMessage = String.format("Error while extracting compressed file %s.",
						compressedCorpus);
				this.logger.error(errorExtractingDataMessage);
				FileUtils.writeStringToFile(sessionFile, errorExtractingDataMessage);
			}
		} else {
			final String contentTypeNotRetrievedMessage = String.format("Cannot retrieve content type of %s.",
					compressedCorpus);
			this.logger.error(contentTypeNotRetrievedMessage);
			FileUtils.writeStringToFile(sessionFile, contentTypeNotRetrievedMessage);
		}
		try {
			Files.deleteIfExists(compressedCorpus);
		} catch (IOException e) {
			final String compressedCorpusNotDeletable = String.format(
					"Cannot delete compressed corpus after upload: %s.", compressedCorpus);
			this.logger.error(compressedCorpusNotDeletable);
			FileUtils.writeStringToFile(sessionFile, compressedCorpusNotDeletable);
		}
		String okMessage = String.format("Corpus extracted successfully @ %s\n", new Date()) + String.format(
				message.getCorporaFolderPath());
		FileUtils.writeStringToFile(sessionFile, okMessage);
	}
}
