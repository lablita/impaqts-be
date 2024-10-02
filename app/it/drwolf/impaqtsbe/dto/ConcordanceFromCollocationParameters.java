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

public class ConcordanceFromCollocationParameters {
	private Integer collNum;
	private String contextQuery;
	private String leftContext;
	private String rightContext;
	private Integer rank;
	private boolean excludeKwic = true;

	public Integer getCollNum() {
		return collNum;
	}

	public String getContextQuery() {
		return contextQuery;
	}

	public String getLeftContext() {
		return leftContext;
	}

	public Integer getRank() {
		return rank;
	}

	public String getRightContext() {
		return rightContext;
	}

	public boolean isExcludeKwic() {
		return excludeKwic;
	}

	public void setCollNum(Integer collNum) {
		this.collNum = collNum;
	}

	public void setContextQuery(String contextQuery) {
		this.contextQuery = contextQuery;
	}

	public void setExcludeKwic(boolean excludeKwic) {
		this.excludeKwic = excludeKwic;
	}

	public void setLeftContext(String leftContext) {
		this.leftContext = leftContext;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public void setRightContext(String rightContext) {
		this.rightContext = rightContext;
	}
}
