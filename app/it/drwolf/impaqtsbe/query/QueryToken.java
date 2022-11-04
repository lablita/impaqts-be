package it.drwolf.impaqtsbe.query;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

// rappresenta un token in CQL: ciò che c'è tra quadre: []
public class QueryToken extends QueryElement {

	private int minRepetitions = 1; // ripetizioni di questo token, da minRepetitions a maxRepetitions:
	private int maxRepetitions = 1; // es. []{2,3} oppure []{0,1} se opzionale
	private boolean isFilter = false;
	private int filterContextLeft = 0;
	private int filterContextRight = 0;
	private boolean optional = false;

	public QueryToken() {
		super();
	}

	@Override
	@JsonIgnore
	public String getCql() {
		StringBuilder cql = new StringBuilder("[");
		if (!this.getTags().isEmpty()) {
			for (List<QueryTag> andList : this.getTags()) {
				cql.append("(");
				for (QueryTag orEl : andList) {
					cql.append(orEl.getCql() + " | ");
				}
				cql = new StringBuilder(cql.substring(0, cql.length() - 3) + ")");
				cql.append(" & ");
			}
			cql = new StringBuilder(cql.substring(0, cql.length() - 3));
		}
		cql.append("]");
		if (this.minRepetitions != 1 || this.maxRepetitions != 1) {
			cql.append("{" + this.minRepetitions + "," + this.maxRepetitions + "}");
		} else if (this.optional) {
			cql.append("?");
		}
		return cql.toString();
	}

	public int getFilterContextLeft() {
		return this.filterContextLeft;
	}

	public int getFilterContextRight() {
		return this.filterContextRight;
	}

	public boolean getIsFilter() {
		return this.isFilter;
	}

	public int getMaxRepetitions() {
		return this.maxRepetitions;
	}

	public int getMinRepetitions() {
		return this.minRepetitions;
	}

	public boolean getOptional() {
		return this.optional;
	}

	public void setFilterContextLeft(int filterContextLeft) {
		this.filterContextLeft = filterContextLeft;
	}

	public void setFilterContextRight(int filterContextRight) {
		this.filterContextRight = filterContextRight;
	}

	public void setIsFilter(boolean isFilter) {
		this.isFilter = isFilter;
	}

	public void setMaxRepetitions(int maxRepetitions) {
		this.maxRepetitions = maxRepetitions;
	}

	public void setMinRepetitions(int minRepetitions) {
		this.minRepetitions = minRepetitions;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}

}
