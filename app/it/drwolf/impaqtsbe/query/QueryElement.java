package it.drwolf.impaqtsbe.query;

import java.util.ArrayList;
import java.util.List;


// Rappresenta una lista in AND di una lista di elementi in OR. Sottoclassi:
// - QueryStructure rappresenta una structure in CQL: <doc>, <s>, ecc.
// - QueryToken rappresenta un token in CQL: ciò che c'è tra quadre: []
public class QueryElement {

	//boolean isEmpty = true; // token vuoto (matcha qualsiasi token)
	List<List<QueryTag>> tags; // lista in AND di una lista di tag in OR: (q1 | q2) & (q3 | q4)

	public QueryElement() {
		this.tags = new ArrayList<>();
	}

	public List<List<QueryTag>> getTags() {
		return tags;
	}

	public void setTags(List<List<QueryTag>> tags) {
		this.tags = tags;
	}

	public int addTag(QueryTag t, int andGroupPosition) {
		if (andGroupPosition == -1 || andGroupPosition >= tags.size()) {
			List<QueryTag> newList = new ArrayList<>();
			newList.add(t);
			tags.add(newList);
			return tags.size()-1;
		}
		List<QueryTag> iList = tags.get(andGroupPosition);
		iList.add(t);
		tags.set(andGroupPosition,iList);
		return andGroupPosition;
	}

	// nuovo tag in AND
	public int addTag(QueryTag t) {
		return addTag(t,-1);
	}

	// add tag in OR
	public int addTagIntoLastGroup(QueryTag t) {
		return addTag(t, tags.size()-1);
	}

	public String getCql() {
		return null;
	}
}
