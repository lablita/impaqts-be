package it.drwolf.impaqtsbe.dto;

import java.util.HashMap;
import java.util.Map;

public class ReferencePositionResponse {
	private Long tokenNumber;
	private Long documentNumber;
	private Map<String, String> references = new HashMap<>();

	public Long getDocumentNumber() {
		return documentNumber;
	}

	public Map<String, String> getReferences() {
		return references;
	}

	public Long getTokenNumber() {
		return tokenNumber;
	}

	public void setDocumentNumber(Long documentNumber) {
		this.documentNumber = documentNumber;
	}

	public void setReferences(Map<String, String> references) {
		this.references = references;
	}

	public void setTokenNumber(Long tokenNumber) {
		this.tokenNumber = tokenNumber;
	}
}
