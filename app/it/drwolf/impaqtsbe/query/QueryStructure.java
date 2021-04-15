package it.drwolf.impaqtsbe.query;

import java.util.List;

public class QueryStructure extends QueryElement {

	public QueryStructure() {
		super();
	}

	public String getCql() {
		return getCql(null);
	}

	public String getCql(String cqlTokenPattern) {
		String cql = cqlTokenPattern;
		if (cql == null) {
			cql = "";
		}
		if (!tags.isEmpty())
		{
			boolean first = true;
			for (List<QueryTag> andList : tags) {
				if (!first) {
					cql += ")";
				}
				if (first && cqlTokenPattern == null) {
					cql += " (";
				}
				else {
					cql += " within (";
				}
				for (QueryTag orEl : andList) {
					cql += "< " + orEl.getCql() + "/> | ";
				}
				cql = cql.substring(0,cql.length()-3) + ")";
				first = false;
			}
		}
		long count_cp = cql.chars().filter(ch -> ch == ')').count();
		long count_op = cql.chars().filter(ch -> ch == '(').count();
		long diff = count_cp - count_op;
		if (diff > 0) {
			for (long i = 0; i < diff; i++) {
				cql = "(" + cql;
			}
		}
		return cql;
	}

}
