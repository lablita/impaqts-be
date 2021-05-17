package it.drwolf.impaqtsbe.query;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class QueryStructure extends QueryElement {

	public QueryStructure() {
		super();
	}

	@Override
	@JsonIgnore
	public String getCql() {
		return this.getCql(null);
	}

	@JsonIgnore
	public String getCql(String cqlTokenPattern) {
		String cql = cqlTokenPattern;
		if (cql == null) {
			cql = "";
		}
		if (!this.getTags().isEmpty()) {
			boolean first = true;
			for (List<QueryTag> andList : this.getTags()) {
				if (!first) {
					cql += ")";
				}
				if (first && cqlTokenPattern == null) {
					cql += " (";
				} else {
					cql += " within (";
				}
				for (QueryTag orEl : andList) {
					cql += "< " + orEl.getCql() + "/> | ";
				}
				cql = cql.substring(0, cql.length() - 3) + ")";
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
