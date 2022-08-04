package it.drwolf.impaqtsbe.dto;

import java.util.ArrayList;
import java.util.List;

public class QueryResponse {
	private Boolean inProgress = Boolean.TRUE;
	private List<KWICLine> kwicLines = new ArrayList<>();
	private Integer currentSize;
	private List<String> metadataValues = new ArrayList<>();
	private List<CollocationItem> collocations = new ArrayList<>();
	private List<FrequencyItem> frequencies = new ArrayList<>();
	private DescResponse descResponse = new DescResponse();

	public List<CollocationItem> getCollocations() {
		return this.collocations;
	}

	public Integer getCurrentSize() {
		return this.currentSize;
	}

	public DescResponse getDescResponse() {
		return this.descResponse;
	}

	public List<FrequencyItem> getFrequencies() {
		return this.frequencies;
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

	public void setCollocations(List<CollocationItem> collocations) {
		this.collocations = collocations;
	}

	public void setCurrentSize(Integer currentSize) {
		this.currentSize = currentSize;
	}

	public void setDescResponse(DescResponse descResponse) {
		this.descResponse = descResponse;
	}

	public void setFrequencies(List<FrequencyItem> frequencies) {
		this.frequencies = frequencies;
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

}
