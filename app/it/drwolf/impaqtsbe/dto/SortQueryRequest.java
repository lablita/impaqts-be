package it.drwolf.impaqtsbe.dto;

import java.util.ArrayList;
import java.util.List;

public class SortQueryRequest {
    private String attribute;
    private String sortKey;
    private Integer numberTokens;
    private Boolean ignoreCase;
    private Boolean backward;
    private List<SortOption> multilevelSort = new ArrayList<>();
    private Boolean multilevel;

    public Boolean getMultilevel() {
        return this.multilevel;
    }

    public void setMultilevel(Boolean multilevel) {
        this.multilevel = multilevel;
    }

    public String getAttribute() {
        return this.attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public Boolean getBackward() {
        return this.backward;
    }

    public void setBackward(Boolean backward) {
        this.backward = backward;
    }

    public Boolean getIgnoreCase() {
        return this.ignoreCase;
    }

    public void setIgnoreCase(Boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public List<SortOption> getMultilevelSort() {
        return this.multilevelSort;
    }

    public void setMultilevelSort(List<SortOption> multilevelSort) {
        this.multilevelSort = multilevelSort;
    }

    public Integer getNumberTokens() {
        return this.numberTokens;
    }

    public void setNumberTokens(Integer numberTokens) {
        this.numberTokens = numberTokens;
    }

    public String getSortKey() {
        return this.sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

}
