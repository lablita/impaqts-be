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

public class WideContextRequest {
	private String corpusName;
	private Long pos;
	private Integer hitlen;

	public String getCorpusName() {
		return corpusName;
	}

	public Integer getHitlen() {
		return hitlen;
	}

	public Long getPos() {
		return pos;
	}

	public void setCorpusName(String corpusName) {
		this.corpusName = corpusName;
	}

	public void setHitlen(Integer hitlen) {
		this.hitlen = hitlen;
	}

	public void setPos(Long pos) {
		this.pos = pos;
	}
}
