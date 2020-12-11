package it.drwolf.impaqtsbe.dto;

public class QueryRequest {
	private final Integer start = 0;
	private final Integer end = 0;
	private String corpusMetadatum;
	private String word;
	private String corpus;

	public String getCorpus() {
		return this.corpus;
	}

	public String getCorpusMetadatum() {
		return this.corpusMetadatum;
	}

	public Integer getEnd() {
		return this.end;
	}

	public Integer getStart() {
		return this.start;
	}

	public String getWord() {
		return this.word;
	}

	public void setCorpus(String corpus) {
		this.corpus = corpus;
	}

	public void setCorpusMetadatum(String corpusMetadatum) {
		this.corpusMetadatum = corpusMetadatum;
	}

	public void setWord(String word) {
		this.word = word;
	}
}
