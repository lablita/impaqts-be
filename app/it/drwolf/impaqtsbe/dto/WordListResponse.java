package it.drwolf.impaqtsbe.dto;

import java.util.ArrayList;
import java.util.List;

public class WordListResponse {
    private final List<WordListItem> items = new ArrayList<>();
    private Integer totalItems;
    private Long totalFreqs;

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public Long getTotalFreqs() {
        return totalFreqs;
    }

    public void setTotalFreqs(Long totalFreqs) {
        this.totalFreqs = totalFreqs;
    }

    public List<WordListItem> getItems() {
        return items;
    }
}
