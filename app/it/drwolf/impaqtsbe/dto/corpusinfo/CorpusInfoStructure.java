package it.drwolf.impaqtsbe.dto.corpusinfo;

import java.util.ArrayList;
import java.util.List;

public class CorpusInfoStructure {
	private List<CorpusInfoAttribute> attributes = new ArrayList<>();
	private String label;
	private String name;

	public List<CorpusInfoAttribute> getAttributes() {
		return attributes;
	}

	public String getLabel() {
		return label;
	}

	public String getName() {
		return name;
	}

	public void setAttributes(List<CorpusInfoAttribute> attributes) {
		this.attributes = attributes;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setName(String name) {
		this.name = name;
	}
}
