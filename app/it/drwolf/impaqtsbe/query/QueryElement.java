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

package it.drwolf.impaqtsbe.query;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

// Rappresenta una lista in AND di una lista di elementi in OR. Sottoclassi:
// - QueryStructure rappresenta una structure in CQL: <doc>, <s>, ecc.
// - QueryToken rappresenta un token in CQL: ciò che c'è tra quadre: []
public class QueryElement {

	//boolean isEmpty = true; // token vuoto (matcha qualsiasi token)
	private List<List<QueryTag>> tags = new ArrayList<>(); // lista in AND di una lista di tag in OR: (q1 | q2) & (q3 | q4)

	public QueryElement() {
		this.tags = new ArrayList<>();
	}

	public int addTag(QueryTag t, int andGroupPosition) {
		if (andGroupPosition == -1 || andGroupPosition >= this.tags.size()) {
			List<QueryTag> newList = new ArrayList<>();
			newList.add(t);
			this.tags.add(newList);
			return this.tags.size() - 1;
		}
		List<QueryTag> iList = this.tags.get(andGroupPosition);
		iList.add(t);
		this.tags.set(andGroupPosition, iList);
		return andGroupPosition;
	}

	// nuovo tag in AND
	public int addTag(QueryTag t) {
		return this.addTag(t, -1);
	}

	// add tag in OR
	public int addTagIntoLastGroup(QueryTag t) {
		return this.addTag(t, this.tags.size() - 1);
	}

	@JsonIgnore
	public String getCql() {
		return null;
	}

	public List<List<QueryTag>> getTags() {
		return this.tags;
	}

	public void setTags(List<List<QueryTag>> tags) {
		this.tags = tags;
	}
}
