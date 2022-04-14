package it.drwolf.impaqtsbe.dto;

public class SortOption {
	private String attribute;
	private Boolean ignoreCase;
	private Boolean backward;
	private String position;

	public String getAttribute() {
		return this.attribute;
	}

	public Boolean getBackward() {
		return this.backward;
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

	public void setBackward(Boolean backward) {
		this.backward = backward;
	}

	public void setIgnoreCase(Boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	public void setPosition(String position) {
		this.position = position;
	}
}
