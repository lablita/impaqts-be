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

package it.drwolf.impaqtsbe.dto;

import java.util.ArrayList;
import java.util.List;

public class FrequencyResultLine {
	private List<String> word = new ArrayList<>();
	private long freq;
	private long fBar;
	private long noRel;
	private long freqBar;
	private int nBar;
	private float norm;
	private float rel;
	private long relBar;

	public long getFreq() {
		return this.freq;
	}

	public long getFreqBar() {
		return this.freqBar;
	}

	public long getNoRel() {
		return this.noRel;
	}

	public float getNorm() {
		return this.norm;
	}

	public float getRel() {
		return this.rel;
	}

	public long getRelBar() {
		return this.relBar;
	}

	public List<String> getWord() {
		return this.word;
	}

	public long getfBar() {
		return this.fBar;
	}

	public int getnBar() {
		return this.nBar;
	}

	public void setFreq(long freq) {
		this.freq = freq;
	}

	public void setFreqBar(long freqBar) {
		this.freqBar = freqBar;
	}

	public void setNoRel(long noRel) {
		this.noRel = noRel;
	}

	public void setNorm(float norm) {
		this.norm = norm;
	}

	public void setRel(float rel) {
		this.rel = rel;
	}

	public void setRelBar(long relBar) {
		this.relBar = relBar;
	}

	public void setWord(List<String> word) {
		this.word = word;
	}

	public void setfBar(long fBar) {
		this.fBar = fBar;
	}

	public void setnBar(int nBar) {
		this.nBar = nBar;
	}
}

