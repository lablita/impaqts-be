package it.drwolf.impaqtsbe.dto;

import java.util.Objects;

public class KWICLine {
	private String ref;
	private String leftContext;
	private String kwic;
	private String rightContext;

	public KWICLine(String ref, String leftContext, String kwic, String rightContext) {
		this.ref = ref;
		this.leftContext = leftContext;
		this.kwic = kwic;
		this.rightContext = rightContext;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		KWICLine kwicLine = (KWICLine) o;
		return this.getRef().equals(kwicLine.getRef()) && this.getLeftContext().equals(kwicLine.getLeftContext())
				&& this.getKwic().equals(kwicLine.getKwic()) && this.getRightContext()
				.equals(kwicLine.getRightContext());
	}

	public String getKwic() {
		return this.kwic;
	}

	public String getLeftContext() {
		return this.leftContext;
	}

	public String getRef() {
		return this.ref;
	}

	public String getRightContext() {
		return this.rightContext;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getRef(), this.getLeftContext(), this.getKwic(), this.getRightContext());
	}

	public void setKwic(String kwic) {
		this.kwic = kwic;
	}

	public void setLeftContext(String leftContext) {
		this.leftContext = leftContext;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public void setRightContext(String rightContext) {
		this.rightContext = rightContext;
	}
}
