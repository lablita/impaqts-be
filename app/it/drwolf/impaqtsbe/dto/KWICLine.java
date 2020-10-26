package it.drwolf.impaqtsbe.dto;

import com.sketchengine.manatee.KWICLines;
import it.drwolf.impaqtsbe.utils.ContextUtils;

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

	public KWICLine(KWICLines kwicLines) {
		this.ref = kwicLines.get_refs();
		this.leftContext = ContextUtils.strip_tags(kwicLines.get_left());
		this.rightContext = ContextUtils.strip_tags(kwicLines.get_right());
		this.kwic = ContextUtils.strip_tags(kwicLines.get_kwic());
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
