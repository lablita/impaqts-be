package it.drwolf.impaqtsbe.dto.corpusinfo;

public class StructItem {
	private String fullName;
	private String name;
	private Integer count;

	public Integer getCount() {
		return count;
	}

	public String getFullName() {
		return fullName;
	}

	public String getName() {
		return name;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setName(String name) {
		this.name = name;
	}
}
