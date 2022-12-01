package it.drwolf.impaqtsbe.dto;

public class WideContextRequest {
	private String corpusName;
	private Long pos;
	private Integer hitlen;

	public String getCorpusName() {
		return corpusName;
	}

	public Integer getHitlen() {
		return hitlen;
	}

	public Long getPos() {
		return pos;
	}

	public void setCorpusName(String corpusName) {
		this.corpusName = corpusName;
	}

	public void setHitlen(Integer hitlen) {
		this.hitlen = hitlen;
	}

	public void setPos(Long pos) {
		this.pos = pos;
	}
}
