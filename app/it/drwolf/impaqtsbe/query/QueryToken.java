package it.drwolf.impaqtsbe.query;

import java.util.List;

// rappresenta un token in CQL: ciò che c'è tra quadre: []
public class QueryToken extends QueryElement {

	int minRepetitions = 1; // ripetizioni di questo token, da minRepetitions a maxRepetitions:
	int maxRepetitions = 1; // es. []{2,3} oppure []{0,1} se opzionale
	boolean isFilter = false;
	int filterContextLeft = 0;
	int filterContextRight = 0;
	boolean optional = false;

	public boolean getOptional() {
		return  optional;
	}

	public int getMinRepetitions() {
		return minRepetitions;
	}

	public int getMaxRepetitions() {
		return maxRepetitions;
	}

	public boolean getIsFilter() {
		return isFilter;
	}

	public int getFilterContextLeft() {
		return filterContextLeft;
	}

	public int getFilterContextRight() {
		return filterContextRight;
	}

	public void setMinRepetitions(int minRepetitions) {
		this.minRepetitions = minRepetitions;
	}

	public void setMaxRepetitions(int maxRepetitions) {
		this.maxRepetitions = maxRepetitions;
	}

	public void setIsFilter(boolean isFilter) {
		this.isFilter = isFilter;
	}

	public void setFilterContextLeft(int filterContextLeft) {
		this.filterContextLeft = filterContextLeft;
	}

	public void setFilterContextRight(int filterContextRight) {
		this.filterContextRight = filterContextRight;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}

	public QueryToken() {
		super();
	}

	public String getCql() {
		String cql = "[";
		if (!tags.isEmpty())
		{
			for (List<QueryTag> andList : tags)
			{
				cql += "(";
				for (QueryTag orEl : andList)
				{
					cql += orEl.getCql() + " | ";
				}
				cql = cql.substring(0,cql.length()-3) + ")";
				cql += " & ";
			}
			cql = cql.substring(0,cql.length()-3);
		}
		cql = cql + "]";
		if (minRepetitions != 1 || maxRepetitions != 1) {
			cql = cql + "{" + minRepetitions + "," + maxRepetitions + "}";
		} else if (this.optional) {
			cql = cql + "?";
		}
		return cql;
	}

}
