package it.drwolf.impaqtsbe.dto;

public class FilterConcordanceQueryRequest {
	private String filter;
	private String token;
	private Integer from;
	private Integer to;
	private boolean kwic;

	public String getFilter() {
		return this.filter;
	}

	public Integer getFrom() {
		return this.from;
	}

	public Integer getTo() {
		return this.to;
	}

	public String getToken() {
		return this.token;
	}

	public boolean isKwic() {
		return this.kwic;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public void setKwic(boolean kwic) {
		this.kwic = kwic;
	}

	public void setTo(Integer to) {
		this.to = to;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
