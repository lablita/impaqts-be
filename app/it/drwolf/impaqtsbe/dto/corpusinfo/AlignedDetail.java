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

public class AlignedDetail {
	private String name;
	private String languageName;
	private List<PosItem> wposlist = new ArrayList<>();
	private List<PosItem> lopslist = new ArrayList<>();
	private boolean hasCase = false;
	private boolean hasLemma = false;
	private String tagsetdoc;

	public String getLanguageName() {
		return languageName;
	}

	public List<PosItem> getLopslist() {
		return lopslist;
	}

	public String getName() {
		return name;
	}

	public String getTagsetdoc() {
		return tagsetdoc;
	}

	public List<PosItem> getWposlist() {
		return wposlist;
	}

	public boolean isHasCase() {
		return hasCase;
	}

	public boolean isHasLemma() {
		return hasLemma;
	}

	public void setHasCase(boolean hasCase) {
		this.hasCase = hasCase;
	}

	public void setHasLemma(boolean hasLemma) {
		this.hasLemma = hasLemma;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public void setLopslist(List<PosItem> lopslist) {
		this.lopslist = lopslist;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTagsetdoc(String tagsetdoc) {
		this.tagsetdoc = tagsetdoc;
	}

	public void setWposlist(List<PosItem> wposlist) {
		this.wposlist = wposlist;
	}
}

