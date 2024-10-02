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

public class DescResponse {
	private String niceArg;
	private String toUrl;
	private float rel;
	private String arg;
	private String operation;
	private long size;
	private String attribute;
	private String term;
	private String position;
	private String window;
	private Integer tokens;

	public String getArg() {
		return this.arg;
	}

	public String getAttribute() {
		return this.attribute;
	}

	public String getNiceArg() {
		return this.niceArg;
	}

	public String getOperation() {
		return this.operation;
	}

	public String getPosition() {
		return this.position;
	}

	public float getRel() {
		return this.rel;
	}

	public long getSize() {
		return this.size;
	}

	public String getTerm() {
		return this.term;
	}

	public String getToUrl() {
		return this.toUrl;
	}

	public Integer getTokens() {
		return this.tokens;
	}

	public String getWindow() {
		return this.window;
	}

	public void setArg(String arg) {
		this.arg = arg;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public void setNiceArg(String niceArg) {
		this.niceArg = niceArg;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setRel(float rel) {
		this.rel = rel;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public void setToUrl(String toUrl) {
		this.toUrl = toUrl;
	}

	public void setTokens(Integer tokens) {
		this.tokens = tokens;
	}

	public void setWindow(String window) {
		this.window = window;
	}
}
