package it.drwolf.impaqtsbe.dto;

import it.drwolf.impaqtsbe.dto.CollocationItem;
import it.drwolf.impaqtsbe.dto.DescResponse;
import it.drwolf.impaqtsbe.dto.FrequencyItem;
import it.drwolf.impaqtsbe.dto.KWICLine;

import java.util.ArrayList;
import java.util.List;

public class QueryResponse {
	private List<KWICLine> kwicLines = new ArrayList<>();
	private List<CollocationItem> collocations = new ArrayList<>();
	private List<FrequencyItem> frequencies = new ArrayList<>();
	private List<DescResponse> descResponses = new ArrayList<>();
	private Boolean inProgress = Boolean.TRUE;
	private Integer currentSize;
	private List<String> metadataValues = new ArrayList<>();

	public List<CollocationItem> getCollocations() {
		return this.collocations;
	}

	public Integer getCurrentSize() {
		return this.currentSize;
	}

	public List<DescResponse> getDescResponses() {
		return this.descResponses;
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
