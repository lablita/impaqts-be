package it.drwolf.impaqtsbe.dto;

import it.drwolf.impaqtsbe.dto.corpusinfo.CorpusInfo;

import java.util.ArrayList;
import java.util.List;

public class QueryResponse {
	private final List<DescResponse> descResponses = new ArrayList<>();
	private final CorpusInfo corpusInfo = new CorpusInfo();
	private String id; // uuid
	private List<KWICLine> kwicLines = new ArrayList<>();
	private List<CollocationItem> collocations = new ArrayList<>();
	private FrequencyItem frequency = new FrequencyItem();
	private Boolean inProgress = Boolean.TRUE;
	private Integer currentSize;
	private List<String> metadataValues = new ArrayList<>();
	private WideContextResponse wideContextResponse = new WideContextResponse();
	private ErrorResponse errorResponse;
	public QueryResponse(String id) {
		this.id = id;
	}

	public List<CollocationItem> getCollocations() {
		return this.collocations;
	}

	public CorpusInfo getCorpusInfo() {
		return corpusInfo;
	}

	public Integer getCurrentSize() {
		return this.currentSize;
	}

	public List<DescResponse> getDescResponses() {
		return this.descResponses;
	}

	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}

	public FrequencyItem getFrequency() {
		return frequency;
	}

	public String getId() {
		return id;
	}

	public Boolean getInProgress() {
		return this.inProgress;
	}

	public List<KWICLine> getKwicLines() {
		return this.kwicLines;
	}

	public List<String> getMetadataValues() {
		return this.metadataValues;
	}

	public WideContextResponse getWideContextResponse() {
		return wideContextResponse;
	}

	public void setCollocations(List<CollocationItem> collocations) {
		this.collocations = collocations;
	}

	public void setCurrentSize(Integer currentSize) {
		this.currentSize = currentSize;
	}

	public void setErrorResponse(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}

	public void setFrequency(FrequencyItem frequency) {
		this.frequency = frequency;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setInProgress(Boolean inProgress) {
		this.inProgress = inProgress;
	}

	public void setKwicLines(List<KWICLine> kwicLines) {
		this.kwicLines = kwicLines;
	}

	public void setMetadataValues(List<String> metadataValues) {
		this.metadataValues = metadataValues;
	}

	public void setWideContextResponse(WideContextResponse wideContextResponse) {
		this.wideContextResponse = wideContextResponse;
	}

}
