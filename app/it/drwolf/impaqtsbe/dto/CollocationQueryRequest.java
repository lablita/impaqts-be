package it.drwolf.impaqtsbe.dto;

import java.util.List;

public class CollocationQueryRequest {
	private String attribute;
	private Integer rengeFrom;
	private Integer rangeTo;
	private Integer minFreqCorpus;
	private Integer minFreqRange;
	private List<String> showFunc;
	private String sortBy;

	public String getAttribute() {
		return this.attribute;
	}

	public Integer getMinFreqCorpus() {
		return this.minFreqCorpus;
	}

	public Integer getMinFreqRange() {
		return this.minFreqRange;
	}

	public Integer getRangeTo() {
		return this.rangeTo;
	}

	public Integer getRengeFrom() {
		return this.rengeFrom;
	}

	public List<String> getShowFunc() {
		return this.showFunc;
	}

	public String getSortBy() {
		return this.sortBy;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public void setMinFreqCorpus(Integer minFreqCorpus) {
		this.minFreqCorpus = minFreqCorpus;
	}

	public void setMinFreqRange(Integer minFreqRange) {
		this.minFreqRange = minFreqRange;
	}

	public void setRangeTo(Integer rangeTo) {
		this.rangeTo = rangeTo;
	}

	public void setRengeFrom(Integer rengeFrom) {
		this.rengeFrom = rengeFrom;
	}

	public void setShowFunc(List<String> showFunc) {
		this.showFunc = showFunc;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
}
