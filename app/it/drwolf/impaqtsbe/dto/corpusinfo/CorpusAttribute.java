package it.drwolf.impaqtsbe.dto.corpusinfo;

public class CorpusAttribute {
	private String name;
	private String label;
	private String dynamic;
	private String fromattr;
	private Integer idRange;

	public String getDynamic() {
		return dynamic;
	}

	public String getFromattr() {
		return fromattr;
	}

	public Integer getIdRange() {
		return idRange;
	}

	public String getLabel() {
		return label;
	}

	public String getName() {
		return name;
	}

	public void setDynamic(String dynamic) {
		this.dynamic = dynamic;
	}

	public void setFromattr(String fromattr) {
		this.fromattr = fromattr;
	}

	public void setIdRange(Integer idRange) {
		this.idRange = idRange;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setName(String name) {
		this.name = name;
	}
}
