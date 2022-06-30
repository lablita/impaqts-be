package it.drwolf.impaqtsbe.dto;

import java.util.ArrayList;
import java.util.List;

public class FrequencyQueryRequest {
	private Integer frequencyLimit;
	private Boolean includeCategories;
	private List<String> categories = new ArrayList<>();
	private List<FrequencyOption> multilevelFrequency = new ArrayList<>();

	public List<String> getCategories() {
		return this.categories;
	}

	public Integer getFrequencyLimit() {
		return this.frequencyLimit;
	}

	public Boolean getIncludeCategories() {
		return this.includeCategories;
	}

	public List<FrequencyOption> getMultilevelFrequency() {
		return this.multilevelFrequency;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public void setFrequencyLimit(Integer frequencyLimit) {
		this.frequencyLimit = frequencyLimit;
	}

	public void setIncludeCategories(Boolean includeCategories) {
		this.includeCategories = includeCategories;
	}

	public void setMultilevelFrequency(List<FrequencyOption> multilevelFrequency) {
		this.multilevelFrequency = multilevelFrequency;
	}
}
