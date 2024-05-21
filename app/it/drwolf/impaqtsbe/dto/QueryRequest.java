package it.drwolf.impaqtsbe.dto;

import it.drwolf.impaqtsbe.query.QueryPattern;

public class QueryRequest {
	private String id; // uuid
	private String corpusMetadatum;
	private Integer start = 0;
	private Integer end = 0;
	private String corpus;
	private String cql;
	private boolean queryInCql = false;
	private QueryPattern queryPattern;
	private CollocationQueryRequest collocationQueryRequest;
	private SortQueryRequest sortQueryRequest;
	private ContextConcordanceQueryRequest contextConcordanceQueryRequest;
	private FrequencyQueryRequest frequencyQueryRequest;
	private WideContextRequest wideContextRequest = new WideContextRequest();
	private ReferencePositionRequest referencePositionRequest = new ReferencePositionRequest();
	private ConcordanceFromCollocationParameters concordanceFromCollocationParameters;
	private FilterConcordanceQueryRequest filterConcordanceQueryRequest;
	private String queryType;
	private WordListRequest wordListRequest;
	private ViewOptionRequest viewOptionRequest;
	private boolean impaqts;

	public CollocationQueryRequest getCollocationQueryRequest() {
		return this.collocationQueryRequest;
	}

	public ConcordanceFromCollocationParameters getConcordanceFromCollocationParameters() {
		return this.concordanceFromCollocationParameters;
	}

	public ContextConcordanceQueryRequest getContextConcordanceQueryRequest() {
		return this.contextConcordanceQueryRequest;
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

	public FilterConcordanceQueryRequest getFilterConcordanceQueryRequest() {
		return this.filterConcordanceQueryRequest;
	}

	public FrequencyQueryRequest getFrequencyQueryRequest() {
		return this.frequencyQueryRequest;
	}

	public String getId() {
		return this.id;
	}

	public boolean getQueryInCql() {
		return this.queryInCql;
	}

	public QueryPattern getQueryPattern() {
		return this.queryPattern;
	}

	public String getQueryType() {
		return this.queryType;
	}

	public ReferencePositionRequest getReferencePositionRequest() {
		return referencePositionRequest;
	}

	public SortQueryRequest getSortQueryRequest() {
		return this.sortQueryRequest;
	}

	public Integer getStart() {
		return this.start;
	}

	public ViewOptionRequest getViewOptionRequest() {
		return viewOptionRequest;
	}

	public WideContextRequest getWideContextRequest() {
		return this.wideContextRequest;
	}

	public WordListRequest getWordListRequest() {
		return this.wordListRequest;
	}

	public boolean isImpaqts() {
		return impaqts;
	}

	public boolean isQueryInCql() {
		return this.queryInCql;
	}

	public void setCollocationQueryRequest(CollocationQueryRequest collocationQueryRequest) {
		this.collocationQueryRequest = collocationQueryRequest;
	}

	public void setConcordanceFromCollocationParameters(
			ConcordanceFromCollocationParameters concordanceFromCollocationParameters) {
		this.concordanceFromCollocationParameters = concordanceFromCollocationParameters;
	}

	public void setContextConcordanceQueryRequest(ContextConcordanceQueryRequest contextConcordanceQueryRequest) {
		this.contextConcordanceQueryRequest = contextConcordanceQueryRequest;
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

	public void setFilterConcordanceQueryRequest(FilterConcordanceQueryRequest filterConcordanceQueryRequest) {
		this.filterConcordanceQueryRequest = filterConcordanceQueryRequest;
	}

	public void setFrequencyQueryRequest(FrequencyQueryRequest frequencyQueryRequest) {
		this.frequencyQueryRequest = frequencyQueryRequest;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setImpaqts(boolean impaqts) {
		this.impaqts = impaqts;
	}

	public void setQueryInCql(boolean queryInCql) {
		this.queryInCql = queryInCql;
	}

	public void setQueryPattern(QueryPattern queryPattern) {
		this.queryPattern = queryPattern;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public void setReferencePositionRequest(ReferencePositionRequest referencePositionRequest) {
		this.referencePositionRequest = referencePositionRequest;
	}

	public void setSortQueryRequest(SortQueryRequest sortQueryRequest) {
		this.sortQueryRequest = sortQueryRequest;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public void setViewOptionRequest(ViewOptionRequest viewOptionRequest) {
		this.viewOptionRequest = viewOptionRequest;
	}

	public void setWideContextRequest(WideContextRequest wideContextRequest) {
		this.wideContextRequest = wideContextRequest;
	}

	public void setWordListRequest(WordListRequest wordListRequest) {
		this.wordListRequest = wordListRequest;
	}

	public enum RequestType {
		VISUAL_QUERY_REQUEST, TEXTUAL_QUERY_REQUEST, CONTEXT_QUERY_REQUEST, COLLOCATION_REQUEST, SORT_REQUEST, MULTI_FREQUENCY_QUERY_REQUEST, METADATA_FREQUENCY_QUERY_REQUEST, PN_MULTI_FREQ_CONCORDANCE_QUERY_REQUEST, PN_METADATA_FREQ_CONCORDANCE_QUERY_REQUEST, WIDE_CONTEXT_QUERY_REQUEST, CORPUS_INFO, FILTER_CONCORDANCE_QUERY_REQUEST, WORD_LIST_REQUEST, IMPLICIT_REQUEST;

		public static boolean contain(String dir) {
			try {
				RequestType.valueOf(dir);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	}
}
