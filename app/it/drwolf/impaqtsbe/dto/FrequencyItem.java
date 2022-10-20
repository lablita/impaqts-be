package it.drwolf.impaqtsbe.dto;

import java.util.ArrayList;
import java.util.List;

public class FrequencyItem {
    private String head;
    private int total;
    private long totalFreq;
    private long maxFreq;
    private float maxRel;
    private List<FrequencyResultLine> items = new ArrayList<>();

    public float getMaxRel() {
        return this.maxRel;
    }

    public void setMaxRel(float maxRel) {
        this.maxRel = maxRel;
    }

    public long getMaxFreq() {
        return this.maxFreq;
    }

    public void setMaxFreq(long maxFreq) {
        this.maxFreq = maxFreq;
    }

    public String getHead() {
        return this.head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public List<FrequencyResultLine> getItems() {
        return this.items;
    }

    public void setItems(List<FrequencyResultLine> items) {
        this.items = items;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public long getTotalFreq() {
        return this.totalFreq;
    }

    public void setTotalFreq(long totalFreq) {
        this.totalFreq = totalFreq;
    }
}
