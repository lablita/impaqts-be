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

public class QueryPattern {
	// sono i blocchetti visuali
	private List<QueryToken> tokPattern = new ArrayList<>();

	// filtro alto livello. Esempio: tipo di implicito
	private QueryStructure structPattern = new QueryStructure();

	public QueryPattern() {
		// empty on purpose
	}

	@JsonIgnore
	public String getCql() {
		StringBuilder cql = new StringBuilder();
		if (this.tokPattern != null && !this.tokPattern.isEmpty()) {
			// caso in cui ho esattamente 2 token e il secondo è un filtro con contesto
			if (this.tokPattern.size() == 2 && this.tokPattern.get(1).getIsFilter()
					&& this.tokPattern.get(0).getMinRepetitions() == 1
					&& this.tokPattern.get(0).getMaxRepetitions() == 1
					&& this.tokPattern.get(1).getMinRepetitions() == 1
					&& this.tokPattern.get(1).getMaxRepetitions() == 1) {
				cql.append("(meet " + this.tokPattern.get(0).getCql() + " " + this.tokPattern.get(1).getCql());
				cql.append(" -" + this.tokPattern.get(1).getFilterContextLeft() + " " + this.tokPattern.get(1)
						.getFilterContextRight() + ")");
			}
			// caso normale
			else {
				for (QueryToken t : this.tokPattern) {
					cql.append(t.getCql());
				}
			}
			if (this.structPattern != null) {
				cql = new StringBuilder(this.structPattern.getCql(cql.toString()));
			}
		}
		// caso in cui faccio la query solo sulla struttura
		else if (this.structPattern != null) {
			cql = new StringBuilder(this.structPattern.getCql());
		}
		return cql.toString();
	}

	public QueryStructure getStructPattern() {
		return this.structPattern;
	}

	public List<QueryToken> getTokPattern() {
		return this.tokPattern;
	}

	public void setStructPattern(QueryStructure structPattern) {
		this.structPattern = structPattern;
	}

	public void setTokPattern(List<QueryToken> tokPattern) {
		this.tokPattern = tokPattern;
	}
}
