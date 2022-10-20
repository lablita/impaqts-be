package it.drwolf.impaqtsbe.dto;

public class ContextConcordanceQueryRequest {
	private String window;
	private int token;
	private String lemma;
	private String item;

	public String getItem() {
		return this.item;
	}

	public String getLemma() {
		return this.lemma;
	}

	public int getToken() {
		return this.token;
	}

	public String getWindow() {
		return this.window;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public void setLemma(String lemma) {
		this.lemma = lemma;
	}

	public void setToken(int token) {
		this.token = token;
	}

	public void setWindow(String window) {
		this.window = window;
	}
}
