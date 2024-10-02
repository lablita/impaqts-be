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

public class ContextConcordanceItem {
	private String window;
	private Integer tokens;
	private String term;
	private String attribute; // WORD, LEMMA, ...
	private String lemmaFilterType;

	public String getAttribute() {
		return this.attribute;
	}

	public String getLemmaFilterType() {
		return this.lemmaFilterType;
	}

	public String getTerm() {
		return this.term;
	}

	public Integer getTokens() {
		return this.tokens;
	}

	public String getWindow() {
		return this.window;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public void setLemmaFilterType(String lemmaFilterType) {
		this.lemmaFilterType = lemmaFilterType;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public void setTokens(Integer tokens) {
		this.tokens = tokens;
	}

	public void setWindow(String window) {
		this.window = window;
	}
}
