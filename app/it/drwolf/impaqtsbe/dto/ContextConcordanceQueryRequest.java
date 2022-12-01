package it.drwolf.impaqtsbe.dto;

import java.util.ArrayList;
import java.util.List;

public class ContextConcordanceQueryRequest {
	private List<ContextConcordanceItem> items = new ArrayList<>();

	public List<ContextConcordanceItem> getItems() {
		return items;
	}

	public void setItems(List<ContextConcordanceItem> items) {
		this.items = items;
	}
}
