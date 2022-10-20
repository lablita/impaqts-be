package it.drwolf.impaqtsbe.dto;

public class FrequencyOption {
	private String attribute;
	private Boolean ignoreCase;
	private String position;

	public String getAttribute() {
		return this.attribute;
	}

	public Boolean getIgnoreCase() {
		return this.ignoreCase;
	}

	public String getPosition() {
		return this.position;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public void setIgnoreCase(Boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	public void setPosition(String position) {
		this.position = position;
	}
}
