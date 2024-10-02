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

public class QueryTag {
	// menu a tendina sopra il testo
	String name; // nome del tag (es. pos, lemma, ...)
	// il testo inserito da cercare
	String value; // valore del tag ricercato

	String structure = "token"; // struttura a cui appartiene il metadato (a livello di token, di sentence, di documento,...)
	boolean startsWithValue = false; // il tag deve iniziare per value (es. value = "sa", word = "sa.*")
	boolean endsWithValue = false; // il tag deve finire per value
	boolean containsValue = false; // il tag deve contenere value
	boolean matchCase = true; // il valore non ignora maiuscole/minuscole
	boolean negation = false; // il token è != e non =

	boolean regexp = false;
	boolean noregexp = false;
	String defaultAttributeCQL;

	public QueryTag() {

	}

	public QueryTag(String name) {
		this.name = name;
	}

	public QueryTag(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public QueryTag(String name, String value, String structure) {
		this.name = name;
		this.value = value;
		this.structure = structure;
	}

	public boolean getContainsValue() {
		return this.containsValue;
	}

	@JsonIgnore
	public String getCql() {
		String val = this.value;
		if (this.containsValue) {
			val = ".*" + val + ".*";
		} else if (this.startsWithValue) {
			val = val + ".*";
		} else if (this.endsWithValue) {
			val = ".*" + val;
		}
		if (!this.matchCase) {
			val = "(?i)" + val;
		}
		String op = "=";
		if (this.negation) {
			op = "!=";
		}
		if (!this.structure.equals("token")) {
			return this.structure + " " + this.name + op + "\"" + val + "\"";
		}
		return this.name + op + "\"" + val + "\"";
	}

	public String getDefaultAttributeCQL() {
		return this.defaultAttributeCQL;
	}

	public boolean getEndsWithValue() {
		return this.endsWithValue;
	}

	public boolean getMatchCase() {
		return this.matchCase;
	}

	public String getName() {
		return this.name;
	}

	public boolean getNegation() {
		return this.negation;
	}

	public boolean getStartsWithValue() {
		return this.startsWithValue;
	}

	public String getStructure() {
		return this.structure;
	}

	public String getValue() {
		return this.value;
	}

	public boolean isContainsValue() {
		return this.containsValue;
	}

	public boolean isEndsWithValue() {
		return this.endsWithValue;
	}

	public boolean isMatchCase() {
		return this.matchCase;
	}

	public boolean isNegation() {
		return this.negation;
	}

	public boolean isNoregexp() {
		return this.noregexp;
	}

	public boolean isRegexp() {
		return this.regexp;
	}

	public boolean isStartsWithValue() {
		return this.startsWithValue;
	}

	public void setContainsValue(boolean x) {
		this.containsValue = x;
	}

	public void setDefaultAttributeCQL(String defaultAttributeCQL) {
		this.defaultAttributeCQL = defaultAttributeCQL;
	}

	public void setEndsWithValue(boolean x) {
		this.endsWithValue = x;
	}

	public void setMatchCase(boolean x) {
		this.matchCase = x;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNegation(boolean negation) {
		this.negation = negation;
	}

	public void setNoregexp(boolean noregexp) {
		this.noregexp = noregexp;
	}

	public void setRegexp(boolean regexp) {
		this.regexp = regexp;
	}

	public void setStartsWithValue(boolean x) {
		this.startsWithValue = x;
	}

	public void setStructure(String x) {
		this.structure = x;
	}

	public void setValue(String x) {
		this.value = x;
	}
}