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
