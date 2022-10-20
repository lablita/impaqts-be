package it.drwolf.impaqtsbe.dto;

public class ConcordanceFromCollocationParameters {
    private Integer collNum;
    private String contextQuery;
    private String leftContext;
    private String rightContext;

    public Integer getCollNum() {
        return collNum;
    }

    public void setCollNum(Integer collNum) {
        this.collNum = collNum;
    }

    public String getContextQuery() {
        return contextQuery;
    }

    public void setContextQuery(String contextQuery) {
        this.contextQuery = contextQuery;
    }

    public String getLeftContext() {
        return leftContext;
    }

    public void setLeftContext(String leftContext) {
        this.leftContext = leftContext;
    }

    public String getRightContext() {
        return rightContext;
    }

    public void setRightContext(String rightContext) {
        this.rightContext = rightContext;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public boolean isExcludeKwic() {
        return excludeKwic;
    }

    public void setExcludeKwic(boolean excludeKwic) {
        this.excludeKwic = excludeKwic;
    }

    private Integer rank;
    private boolean excludeKwic = true;
}
