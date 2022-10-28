package it.drwolf.impaqtsbe.dto;

public class WideContextResponse {
	private String leftContext;
	private String kwic;
	private String rightContext;

	public String getKwic() {
		return kwic;
	}

	public String getLeftContext() {
		return leftContext;
	}

	public String getRightContext() {
		return rightContext;
	}

	public void setKwic(String kwic) {
		this.kwic = kwic;
	}

	public void setLeftContext(String leftContext) {
		this.leftContext = leftContext;
	}

	public void setRightContext(String rightContext) {
		this.rightContext = rightContext;
	}
}
