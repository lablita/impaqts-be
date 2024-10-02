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

public class FrequencyOption {
	private String attribute;
	private Boolean ignoreCase;
	private String position;
	private String term;

	public String getAttribute() {
		return this.attribute;
	}

	public Boolean getIgnoreCase() {
		return this.ignoreCase;
	}

	public String getPosition() {
		return this.position;
	}

	public String getTerm() {
		return this.term;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public void setIgnoreCase(Boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setTerm(String term) {
		this.term = term;
	}
}
