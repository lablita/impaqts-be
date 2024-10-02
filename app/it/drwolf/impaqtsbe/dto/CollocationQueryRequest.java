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

import java.util.List;

public class CollocationQueryRequest {
	private String attribute;
	private Integer rangeFrom;
	private Integer rangeTo;
	private Integer minFreqCorpus;
	private Integer minFreqRange;
	private List<String> showFunc;
	private String sortBy;

	public String getAttribute() {
		return this.attribute;
	}

	public Integer getMinFreqCorpus() {
		return this.minFreqCorpus;
	}

	public Integer getMinFreqRange() {
		return this.minFreqRange;
	}

	public Integer getRangeFrom() {
		return this.rangeFrom;
	}

	public Integer getRangeTo() {
		return this.rangeTo;
	}

	public List<String> getShowFunc() {
		return this.showFunc;
	}

	public String getSortBy() {
		return this.sortBy;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public void setMinFreqCorpus(Integer minFreqCorpus) {
		this.minFreqCorpus = minFreqCorpus;
	}

	public void setMinFreqRange(Integer minFreqRange) {
		this.minFreqRange = minFreqRange;
	}

	public void setRangeFrom(Integer rangeFrom) {
		this.rangeFrom = rangeFrom;
	}

	public void setRangeTo(Integer rangeTo) {
		this.rangeTo = rangeTo;
	}

	public void setShowFunc(List<String> showFunc) {
		this.showFunc = showFunc;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

}
