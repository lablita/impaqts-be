package it.drwolf.impaqtsbe.dto;

public class DescResponse {
	private String niceArg;
	private String toUrl;
	private float rel;
	private String arg;
	private String op;
	private long size;

	public String getArg() {
		return this.arg;
	}

	public String getNiceArg() {
		return this.niceArg;
	}

	public String getOp() {
		return this.op;
	}

	public float getRel() {
		return this.rel;
	}

	public long getSize() {
		return this.size;
	}

	public String getToUrl() {
		return this.toUrl;
	}

	public void setArg(String arg) {
		this.arg = arg;
	}

	public void setNiceArg(String niceArg) {
		this.niceArg = niceArg;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public void setRel(float rel) {
		this.rel = rel;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public void setToUrl(String toUrl) {
		this.toUrl = toUrl;
	}
}
