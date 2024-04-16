package it.drwolf.impaqtsbe.dto;

import java.util.ArrayList;
import java.util.List;

public class ViewOptionRequest {
	private List<String> attributes = new ArrayList<>();

	public List<String> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<String> attributes) {
		this.attributes = attributes;
	}
}
