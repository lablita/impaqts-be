package it.drwolf.impaqtsbe.dto;

import it.drwolf.impaqtsbe.query.QueryPattern;

public class QueryRequest {
	private String corpusMetadatum;
	private Integer start = 0;
	private Integer end = 0;
	private String corpus;
	private String cql;
	private boolean queryInCql = true;
	private QueryPattern queryPattern;

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

	public String getCql() {
		return this.cql;
	}

	public boolean getQueryInCql() {
		return queryInCql;
	}

	public QueryPattern getQueryPattern() {
		return queryPattern;
	}

	public void setCorpus(String corpus) {
		this.corpus = corpus;
	}

	public void setCorpusMetadatum(String corpusMetadatum) {
		this.corpusMetadatum = corpusMetadatum;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public void setCql(String cql) {
		this.cql = cql;
	}

	public void setQueryInCql(boolean queryInCql) {
		this.queryInCql = queryInCql;
	}

	public void setQueryPattern(QueryPattern queryPattern) {
		this.queryPattern = queryPattern;
	}
}
