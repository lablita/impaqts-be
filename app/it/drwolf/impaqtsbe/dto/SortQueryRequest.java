package it.drwolf.impaqtsbe.dto;

import java.util.ArrayList;
import java.util.List;

public class SortQueryRequest {
	private String attribute;
	private String sortKey;
	private Integer numberTokens;
	private Boolean ignoreCase;
	private Boolean backward;
	private List<SortOption> multilevelSort = new ArrayList<>();

	public String getAttribute() {
		return this.attribute;
	}

	public Boolean getBackward() {
		return this.backward;
	}

	public Boolean getIgnoreCase() {
		return this.ignoreCase;
	}

	public List<SortOption> getMultilevelSort() {
		return this.multilevelSort;
	}

	public Integer getNumberTokens() {
		return this.numberTokens;
	}

	public String getSortKey() {
		return this.sortKey;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public void setBackward(Boolean backward) {
		this.backward = backward;
	}

	public void setIgnoreCase(Boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	public void setMultilevelSort(List<SortOption> multilevelSort) {
		this.multilevelSort = multilevelSort;
	}

	public void setNumberTokens(Integer numberTokens) {
		this.numberTokens = numberTokens;
	}

	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}

}
