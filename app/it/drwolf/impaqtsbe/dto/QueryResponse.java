package it.drwolf.impaqtsbe.dto;

import it.drwolf.impaqtsbe.dto.corpusinfo.CorpusInfo;

import java.util.ArrayList;
import java.util.List;

public class QueryResponse {
    private final List<DescResponse> descResponses = new ArrayList<>();
    private final CorpusInfo corpusInfo = new CorpusInfo();
    private Integer start = 0;
    private Integer end = 0;
    private String id; // uuid
    private List<KWICLine> kwicLines = new ArrayList<>();
    private List<CollocationItem> collocations = new ArrayList<>();
    private FrequencyItem frequency = new FrequencyItem();
    private Boolean inProgress = Boolean.TRUE;
    private Integer currentSize;
    private List<String> metadataValues = new ArrayList<>();
    private WideContextResponse wideContextResponse = new WideContextResponse();
    private ErrorResponse errorResponse;
    private WordListResponse wordList = new WordListResponse();
    public QueryResponse() {
    }

    public QueryResponse(String id) {
        this.id = id;
    }

    public WordListResponse getWordList() {
        return wordList;
    }

    public void setWordList(WordListResponse wordList) {
        this.wordList = wordList;
    }

    public List<CollocationItem> getCollocations() {
        return this.collocations;
    }

    public void setCollocations(List<CollocationItem> collocations) {
        this.collocations = collocations;
    }

    public CorpusInfo getCorpusInfo() {
        return corpusInfo;
    }

    public Integer getCurrentSize() {
        return this.currentSize;
    }

    public void setCurrentSize(Integer currentSize) {
        this.currentSize = currentSize;
    }

    public List<DescResponse> getDescResponses() {
        return this.descResponses;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public FrequencyItem getFrequency() {
        return frequency;
    }

    public void setFrequency(FrequencyItem frequency) {
        this.frequency = frequency;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getInProgress() {
        return this.inProgress;
    }

    public void setInProgress(Boolean inProgress) {
        this.inProgress = inProgress;
    }

    public List<KWICLine> getKwicLines() {
        return this.kwicLines;
    }

    public void setKwicLines(List<KWICLine> kwicLines) {
        this.kwicLines = kwicLines;
    }

    public List<String> getMetadataValues() {
        return this.metadataValues;
    }

    public void setMetadataValues(List<String> metadataValues) {
        this.metadataValues = metadataValues;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public WideContextResponse getWideContextResponse() {
        return wideContextResponse;
    }

    public void setWideContextResponse(WideContextResponse wideContextResponse) {
        this.wideContextResponse = wideContextResponse;
    }

}
