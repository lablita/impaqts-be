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

public class FilterConcordanceQueryRequest {
	private String filter;
	private String token;
	private Integer from;
	private Integer to;
	private boolean kwic;

	public String getFilter() {
		return this.filter;
	}

	public Integer getFrom() {
		return this.from;
	}

	public Integer getTo() {
		return this.to;
	}

	public String getToken() {
		return this.token;
	}

	public boolean isKwic() {
		return this.kwic;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public void setKwic(boolean kwic) {
		this.kwic = kwic;
	}

	public void setTo(Integer to) {
		this.to = to;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
