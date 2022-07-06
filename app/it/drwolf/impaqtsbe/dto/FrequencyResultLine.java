package it.drwolf.impaqtsbe.dto;

import java.util.ArrayList;
import java.util.List;

public class FrequencyResultLine {
	private List<String> word = new ArrayList<>();
	private long freq;
	private long fbar;
	private long norel;

	public long getFbar() {
		return this.fbar;
	}

	public long getFreq() {
		return this.freq;
	}

	public long getNorel() {
		return this.norel;
	}

	public List<String> getWord() {
		return this.word;
	}

	public void setFbar(long fbar) {
		this.fbar = fbar;
	}

	public void setFreq(long freq) {
		this.freq = freq;
	}

	public void setNorel(long norel) {
		this.norel = norel;
	}

	public void setWord(List<String> word) {
		this.word = word;
	}
}
