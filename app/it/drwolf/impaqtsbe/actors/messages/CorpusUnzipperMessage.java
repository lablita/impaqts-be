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
