package it.drwolf.impaqtsbe.dto;

public class ReferencePositionRequest {
	private String corpusName;
	private Long pos;

	public String getCorpusName() {
		return corpusName;
	}

	public Long getPos() {
		return pos;
	}

	public void setCorpusName(String corpusName) {
		this.corpusName = corpusName;
	}

	public void setPos(Long pos) {
		this.pos = pos;
	}
}
