package it.drwolf.impaqtsbe.dto;

import java.util.ArrayList;
import java.util.List;

public class ViewOptionRequest {
	private final List<String> attributesKwic = new ArrayList<>();

	private List<String> attributesCtx = new ArrayList<>();

	public List<String> getAttributesCtx() {
		return attributesCtx;
	}

	public void setAttributesCtx(List<String> attributesCtx) {
		this.attributesCtx = attributesCtx;
	}
}
