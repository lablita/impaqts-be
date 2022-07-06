package it.drwolf.impaqtsbe.dto;

import java.util.ArrayList;
import java.util.List;

public class FrequencyItem {
	private String head;
	private int total;
	private long totalFreq;
	private List<FrequencyResultLine> items = new ArrayList<>();

	public String getHead() {
		return this.head;
	}

	public List<FrequencyResultLine> getItems() {
		return this.items;
	}

	public int getTotal() {
		return this.total;
	}

	public long getTotalFreq() {
		return this.totalFreq;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public void setItems(List<FrequencyResultLine> items) {
		this.items = items;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setTotalFreq(long totalFreq) {
		this.totalFreq = totalFreq;
	}
}
