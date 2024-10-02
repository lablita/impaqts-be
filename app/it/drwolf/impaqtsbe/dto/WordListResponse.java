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

public class WordListResponse {
	private final List<WordListItem> items = new ArrayList<>();
	private Integer totalItems;
	private Long totalFreqs;
	private String searchAttribute;

	public List<WordListItem> getItems() {
		return items;
	}

	public String getSearchAttribute() {
		return searchAttribute;
	}

	public Long getTotalFreqs() {
		return totalFreqs;
	}

	public Integer getTotalItems() {
		return totalItems;
	}

	public void setSearchAttribute(String searchAttribute) {
		this.searchAttribute = searchAttribute;
	}

	public void setTotalFreqs(Long totalFreqs) {
		this.totalFreqs = totalFreqs;
	}

	public void setTotalItems(Integer totalItems) {
		this.totalItems = totalItems;
	}
}
