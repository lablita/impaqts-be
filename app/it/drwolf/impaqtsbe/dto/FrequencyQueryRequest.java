package it.drwolf.impaqtsbe.dto;

import java.util.ArrayList;
import java.util.List;

public class FrequencyQueryRequest {
	private Integer frequencyLimit;
	private Boolean categoryWithoutResults;
	private List<String> category = new ArrayList<>();
	private List<FrequencyOption> multilevelFrequency = new ArrayList<>();

	public List<String> getCategory() {
		return this.category;
	}

	public Boolean getCategoryWithoutResults() {
		return this.categoryWithoutResults;
	}

	public Integer getFrequencyLimit() {
		return this.frequencyLimit;
	}

	public List<FrequencyOption> getMultilevelFrequency() {
		return this.multilevelFrequency;
	}

	public void setCategory(List<String> category) {
		this.category = category;
	}

	public void setCategoryWithoutResults(Boolean categoryWithoutResults) {
		this.categoryWithoutResults = categoryWithoutResults;
	}

	public void setFrequencyLimit(Integer frequencyLimit) {
		this.frequencyLimit = frequencyLimit;
	}

	public void setMultilevelFrequency(List<FrequencyOption> multilevelFrequency) {
		this.multilevelFrequency = multilevelFrequency;
	}
}
