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

	}

	@JsonIgnore
	public String getCql() {
		String cql = "";
		if (this.tokPattern != null && !this.tokPattern.isEmpty()) {
			// caso in cui ho esattamente 2 token e il secondo è un filtro con contesto
			if (this.tokPattern.size() == 2 && this.tokPattern.get(1).getIsFilter()
					&& this.tokPattern.get(0).getMinRepetitions() == 1
					&& this.tokPattern.get(0).getMaxRepetitions() == 1
					&& this.tokPattern.get(1).getMinRepetitions() == 1
					&& this.tokPattern.get(1).getMaxRepetitions() == 1) {
				cql = "(meet " + this.tokPattern.get(0).getCql() + " " + this.tokPattern.get(1).getCql();
				cql += " -" + this.tokPattern.get(1).getFilterContextLeft() + " " + this.tokPattern.get(1)
						.getFilterContextRight() + ")";
			}
			// caso normale
			else {
				for (QueryToken t : this.tokPattern) {
					cql += t.getCql();
				}
			}
			if (this.structPattern != null) {
				cql = this.structPattern.getCql(cql);
			}
		}
		// caso in cui faccio la query solo sulla struttura
		else if (this.structPattern != null) {
			cql = this.structPattern.getCql();
		}
		return cql;
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

	public void setTokPattern(ArrayList<QueryToken> tokPattern) {
		this.tokPattern = tokPattern;
	}
}
