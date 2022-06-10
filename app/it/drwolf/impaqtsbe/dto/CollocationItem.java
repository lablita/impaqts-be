package it.drwolf.impaqtsbe.dto;

public class CollocationItem {

	private String positiveFilter;
	private String negativeFilter;
	private String word;
	private Long concurrenceCount;
	private Long candidateCount;
	private Double tScore;
	private Double mi;
	private Double mi3;
	private Double logLikelihood;
	private Double minSensitivity;
	private Double logDice;
	private Double miLogF;

	public Long getCandidateCount() {
		return this.candidateCount;
	}

	public Long getConcurrenceCount() {
		return this.concurrenceCount;
	}

	public Double getLogDice() {
		return this.logDice;
	}

	public Double getLogLikelihood() {
		return this.logLikelihood;
	}

	public Double getMi() {
		return this.mi;
	}

	public Double getMi3() {
		return this.mi3;
	}

	public Double getMiLogF() {
		return this.miLogF;
	}

	public Double getMinSensitivity() {
		return this.minSensitivity;
	}

	public String getNegativeFilter() {
		return this.negativeFilter;
	}

	public String getPositiveFilter() {
		return this.positiveFilter;
	}

	public Double getTScore() {
		return this.tScore;
	}

	public String getWord() {
		return this.word;
	}

	public void setCandidateCount(Long candidateCount) {
		this.candidateCount = candidateCount;
	}

	public void setConcurrenceCount(Long concurrenceCount) {
		this.concurrenceCount = concurrenceCount;
	}

	public void setLogDice(Double logDice) {
		this.logDice = logDice;
	}

	public void setLogLikelihood(Double logLikelihood) {
		this.logLikelihood = logLikelihood;
	}

	public void setMi(Double mi) {
		this.mi = mi;
	}

	public void setMi3(Double mi3) {
		this.mi3 = mi3;
	}

	public void setMiLogF(Double miLogF) {
		this.miLogF = miLogF;
	}

	public void setMinSensitivity(Double minSensitivity) {
		this.minSensitivity = minSensitivity;
	}

	public void setNegativeFilter(String negativeFilter) {
		this.negativeFilter = negativeFilter;
	}

	public void setPositiveFilter(String positiveFilter) {
		this.positiveFilter = positiveFilter;
	}

	public void setTScore(Double tScore) {
		this.tScore = tScore;
	}

	public void setWord(String word) {
		this.word = word;
	}
}
