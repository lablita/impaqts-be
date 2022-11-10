package it.drwolf.impaqtsbe.dto;

public class ContextConcordanceItem {
	private String window;
	private Integer tokens;
	private String term;
	private String attribute; // WORD, LEMMA, ...
	private String item;

	public String getAttribute() {
		return this.attribute;
	}

	public String getItem() {
		return this.item;
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

	public void setItem(String item) {
		this.item = item;
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
