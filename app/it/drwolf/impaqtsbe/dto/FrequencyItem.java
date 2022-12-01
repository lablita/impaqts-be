package it.drwolf.impaqtsbe.dto;

import java.util.ArrayList;
import java.util.List;

public class FrequencyItem {
	private String head;
	private int total;
	private long totalFreq;
	private long maxFreq;
	private float maxRel;
	private String operation;
	private List<FrequencyResultLine> items = new ArrayList<>();

	public String getHead() {
		return this.head;
	}

	public List<FrequencyResultLine> getItems() {
		return this.items;
	}

	public long getMaxFreq() {
		return this.maxFreq;
	}

	public float getMaxRel() {
		return this.maxRel;
	}

	public String getOperation() {
		return this.operation;
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

	public void setMaxFreq(long maxFreq) {
		this.maxFreq = maxFreq;
	}

	public void setMaxRel(float maxRel) {
		this.maxRel = maxRel;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setTotalFreq(long totalFreq) {
		this.totalFreq = totalFreq;
	}
}
