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
