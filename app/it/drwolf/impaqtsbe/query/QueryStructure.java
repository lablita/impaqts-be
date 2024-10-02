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
