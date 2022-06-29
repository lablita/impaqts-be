package it.drwolf.impaqtsbe.dto;

import it.drwolf.impaqtsbe.query.QueryPattern;

public class QueryRequest {
	private String corpusMetadatum;
	private Integer start = 0;
	private Integer end = 0;
	private String corpus;
	private String cql;
	private boolean queryInCql = false;
	private QueryPattern queryPattern;
	private CollocationQueryRequest collocationQueryRequest;
	private SortQueryRequest sortQueryRequest;

	private FrequencyQueryRequest frequencyQueryRequest;

	public CollocationQueryRequest getCollocationQueryRequest() {
		return this.collocationQueryRequest;
	}

	public String getCorpus() {
		return this.corpus;
	}

	public String getCorpusMetadatum() {
		return this.corpusMetadatum;
	}

	public String getCql() {
		return this.cql;
	}

	public Integer getEnd() {
		return this.end;
	}

	public FrequencyQueryRequest getFrequencyQueryRequest() {
		return this.frequencyQueryRequest;
	}

	public boolean getQueryInCql() {
		return this.queryInCql;
	}

	public QueryPattern getQueryPattern() {
		return this.queryPattern;
	}

	public SortQueryRequest getSortQueryRequest() {
		return this.sortQueryRequest;
	}

	public Integer getStart() {
		return this.start;
	}

	public boolean isQueryInCql() {
		return this.queryInCql;
	}

	public void setCollocationQueryRequest(CollocationQueryRequest collocationQueryRequest) {
		this.collocationQueryRequest = collocationQueryRequest;
	}

	public void setCorpus(String corpus) {
		this.corpus = corpus;
	}

	public void setCorpusMetadatum(String corpusMetadatum) {
		this.corpusMetadatum = corpusMetadatum;
	}

	public void setCql(String cql) {
		this.cql = cql;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public void setFrequencyQueryRequest(FrequencyQueryRequest frequencyQueryRequest) {
		this.frequencyQueryRequest = frequencyQueryRequest;
	}

	public void setQueryInCql(boolean queryInCql) {
		this.queryInCql = queryInCql;
	}

	public void setQueryPattern(QueryPattern queryPattern) {
		this.queryPattern = queryPattern;
	}

	public void setSortQueryRequest(SortQueryRequest sortQueryRequest) {
		this.sortQueryRequest = sortQueryRequest;
	}

	public void setStart(Integer start) {
		this.start = start;
	}
}
