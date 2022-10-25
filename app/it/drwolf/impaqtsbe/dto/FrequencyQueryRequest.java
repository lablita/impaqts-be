package it.drwolf.impaqtsbe.dto;

import java.util.ArrayList;
import java.util.List;

public class FrequencyQueryRequest {
	private Integer frequencyLimit;
	private Boolean includeCategoriesWithNoHits;
	private String frequencyType;
	private Integer frequencyColSort;
	private String frequencyTypeSort;
	private String category;
	private List<FrequencyOption> multilevelFrequency = new ArrayList<>();

	public String getCategory() {
		return this.category;
	}

	public Integer getFrequencyColSort() {
		return this.frequencyColSort;
	}

	public Integer getFrequencyLimit() {
		return this.frequencyLimit;
	}

	public String getFrequencyType() {
		return frequencyType;
	}

	public String getFrequencyTypeSort() {
		return this.frequencyTypeSort;
	}

	public Boolean getIncludeCategoriesWithNoHits() {
		return this.includeCategoriesWithNoHits;
	}

	public List<FrequencyOption> getMultilevelFrequency() {
		return this.multilevelFrequency;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setFrequencyColSort(Integer frequencyColSort) {
		this.frequencyColSort = frequencyColSort;
	}

	public void setFrequencyLimit(Integer frequencyLimit) {
		this.frequencyLimit = frequencyLimit;
	}

	public void setFrequencyType(String frequencyType) {
		this.frequencyType = frequencyType;
	}

	public void setFrequencyTypeSort(String frequencyTypeSort) {
		this.frequencyTypeSort = frequencyTypeSort;
	}

	public void setIncludeCategoriesWithNoHits(Boolean includeCategoriesWithNoHits) {
		this.includeCategoriesWithNoHits = includeCategoriesWithNoHits;
	}

	public void setMultilevelFrequency(List<FrequencyOption> multilevelFrequency) {
		this.multilevelFrequency = multilevelFrequency;
	}
}
