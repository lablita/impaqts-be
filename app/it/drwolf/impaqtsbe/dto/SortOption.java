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

public class SortOption {
	private String attribute;
	private Boolean ignoreCase;
	private Boolean backward;
	private String position;

	public String getAttribute() {
		return this.attribute;
	}

	public Boolean getBackward() {
		return this.backward;
	}

	public Boolean getIgnoreCase() {
		return this.ignoreCase;
	}

	public String getPosition() {
		return this.position;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public void setBackward(Boolean backward) {
		this.backward = backward;
	}

	public void setIgnoreCase(Boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	public void setPosition(String position) {
		this.position = position;
	}
}
