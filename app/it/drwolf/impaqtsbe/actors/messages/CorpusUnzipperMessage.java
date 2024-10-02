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

package it.drwolf.impaqtsbe.actors.messages;

import play.libs.Files;
import play.mvc.Http;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

public class CorpusUnzipperMessage {
	List<Http.MultipartFormData.FilePart<Files.TemporaryFile>> compressedCorpusFileParts;
	private UUID sessionUUID;
	private String corporaFolderPath;
	private Path sessionFile;

	public List<Http.MultipartFormData.FilePart<Files.TemporaryFile>> getCompressedCorpusFileParts() {
		return compressedCorpusFileParts;
	}

	public String getCorporaFolderPath() {
		return corporaFolderPath;
	}

	public Path getSessionFile() {
		return sessionFile;
	}

	public UUID getSessionUUID() {
		return sessionUUID;
	}

	public void setCompressedCorpusFileParts(
			List<Http.MultipartFormData.FilePart<Files.TemporaryFile>> compressedCorpusFileParts) {
		this.compressedCorpusFileParts = compressedCorpusFileParts;
	}

	public void setCorporaFolderPath(String corporaFolderPath) {
		this.corporaFolderPath = corporaFolderPath;
	}

	public void setSessionFile(Path sessionFile) {
		this.sessionFile = sessionFile;
	}

	public void setSessionUUID(UUID sessionUUID) {
		this.sessionUUID = sessionUUID;
	}
}
