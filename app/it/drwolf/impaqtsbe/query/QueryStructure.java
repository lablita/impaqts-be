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
		StringBuilder cql = new StringBuilder();
		if (cqlTokenPattern != null) {
			cql = new StringBuilder(cqlTokenPattern);
		}
		if (!this.getTags().isEmpty()) {
			boolean first = true;
			for (List<QueryTag> andList : this.getTags()) {
				if (!first) {
					cql.append(")");
				}
				if (first && cqlTokenPattern == null) {
					cql.append(" (");
				} else {
					cql.append(" within (");
				}
				for (QueryTag orEl : andList) {
					cql.append("< ").append(orEl.getCql()).append("/> | ");
				}
				cql = new StringBuilder(cql.substring(0, cql.length() - 3) + ")");
				first = false;
			}
		}
		long countCp = cql.chars().filter(ch -> ch == ')').count();
		long countOp = cql.chars().filter(ch -> ch == '(').count();
		long diff = countCp - countOp;
		if (diff > 0) {
			for (long i = 0; i < diff; i++) {
				cql = new StringBuilder("(" + cql);
			}
		}
		return cql.toString();
	}

}
