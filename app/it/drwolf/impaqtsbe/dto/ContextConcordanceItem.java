package it.drwolf.impaqtsbe.dto;

public class ContextConcordanceItem {
	private String window;
	private Integer tokens;
	private String term;
	private String attribute; // WORD, LEMMA, ...
	private String lemmaFilterType;

	public String getAttribute() {
		return this.attribute;
	}

	public String getLemmaFilterType() {
		return this.lemmaFilterType;
	}

	public String getTerm() {
		return this.term;
	}

	public Integer getTokens() {
		return this.tokens;
	}

	public String getWindow() {
		return this.window;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public void setLemmaFilterType(String lemmaFilterType) {
		this.lemmaFilterType = lemmaFilterType;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public void setTokens(Integer tokens) {
		this.tokens = tokens;
	}

	public void setWindow(String window) {
		this.window = window;
	}
}
