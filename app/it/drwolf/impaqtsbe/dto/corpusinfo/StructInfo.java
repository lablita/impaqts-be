package it.drwolf.impaqtsbe.dto.corpusinfo;

import java.util.ArrayList;
import java.util.List;

public class StructInfo {
	private String name;
	private Long count;
	private List<StructItem> structItems = new ArrayList<>();

	public Long getCount() {
		return count;
	}

	public String getName() {
		return name;
	}

	public List<StructItem> getStructItems() {
		return structItems;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStructItems(List<StructItem> structItems) {
		this.structItems = structItems;
	}
}
