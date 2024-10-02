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

public class StructItem {
	private String fullName;
	private String name;
	private Integer count;

	public Integer getCount() {
		return count;
	}

	public String getFullName() {
		return fullName;
	}

	public String getName() {
		return name;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setName(String name) {
		this.name = name;
	}
}
