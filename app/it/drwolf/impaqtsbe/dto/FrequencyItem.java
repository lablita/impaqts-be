/*
 * Copyright (C) 2024
 * EMMACorpus
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
