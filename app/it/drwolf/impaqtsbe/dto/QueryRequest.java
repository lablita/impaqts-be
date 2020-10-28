package it.drwolf.impaqtsbe.dto;

public class QueryRequest {
	private final Integer start = 0;
	private final Integer end = 0;
	private String word;

	public Integer getEnd() {
		return this.end;
	}

	public Integer getStart() {
		return this.start;
	}

	public String getWord() {
		return this.word;
	}

	public void setWord(String word) {
		this.word = word;
	}
}
