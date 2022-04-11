package it.drwolf.impaqtsbe.dto;

import java.util.List;

public class SortQueryRequest {
	private String attribute;
	private String sortKey;
	private Integer numberTokens;
	private Boolean ignoreCase;
	private Boolean backward;
	private List<String> levels;
	private List<String> attributeMulti;
	private List<Boolean> ignoreCaseMulti;
	private List<Boolean> backwardMulti;
	private List<String> position;

	public String getAttribute() {
		return this.attribute;
	}

	public List<String> getAttributeMulti() {
		return this.attributeMulti;
	}

	public Boolean getBackward() {
		return this.backward;
	}

	public List<Boolean> getBackwardMulti() {
		return this.backwardMulti;
	}

	public Boolean getIgnoreCase() {
		return this.ignoreCase;
	}

	public List<Boolean> getIgnoreCaseMulti() {
		return this.ignoreCaseMulti;
	}

	public List<String> getLevels() {
		return this.levels;
	}

	public Integer getNumberTokens() {
		return this.numberTokens;
	}

	public List<String> getPosition() {
		return this.position;
	}

	public String getSortKey() {
		return this.sortKey;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public void setAttributeMulti(List<String> attributeMulti) {
		this.attributeMulti = attributeMulti;
	}

	public void setBackward(Boolean backward) {
		this.backward = backward;
	}

	public void setBackwardMulti(List<Boolean> backwardMulti) {
		this.backwardMulti = backwardMulti;
	}

	public void setIgnoreCase(Boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	public void setIgnoreCaseMulti(List<Boolean> ignoreCaseMulti) {
		this.ignoreCaseMulti = ignoreCaseMulti;
	}

	public void setLevels(List<String> levels) {
		this.levels = levels;
	}

	public void setNumberTokens(Integer numberTokens) {
		this.numberTokens = numberTokens;
	}

	public void setPosition(List<String> position) {
		this.position = position;
	}

	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}

}
