package it.drwolf.impaqtsbe.dto;

public class WordListRequest {
    private String subCorpus;
    private String searchAttribute;
    private boolean useNGrams;
    private int nGramFrom;
    private int nGramTo;
    private boolean hideSubNGram;
    private String regexp;
    private int minFreq;
    private int maxFreq;
    private String whiteList;
    private String blackList;
    private boolean nonWords;
    private FigureType freqFigure;
    private OutputType outputType;
    private String kwRefCorpus;
    private String kwRefSubCorpus;
    private float commonWords;
    private ChangeOutputAttrType changeOutFirst;
    private ChangeOutputAttrType changeOutSecond;
    private ChangeOutputAttrType changeOutThird;
    private String sortBy;
    private String sortDir;

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDir() {
        return sortDir;
    }

    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
    }

    public String getBlackList() {
        return this.blackList;
    }

    public void setBlackList(String blackList) {
        this.blackList = blackList;
    }

    public ChangeOutputAttrType getChangeOutFirst() {
        return this.changeOutFirst;
    }

    public void setChangeOutFirst(ChangeOutputAttrType changeOutFirst) {
        this.changeOutFirst = changeOutFirst;
    }

    public ChangeOutputAttrType getChangeOutSecond() {
        return this.changeOutSecond;
    }

    public void setChangeOutSecond(ChangeOutputAttrType changeOutSecond) {
        this.changeOutSecond = changeOutSecond;
    }

    public ChangeOutputAttrType getChangeOutThird() {
        return this.changeOutThird;
    }

    public void setChangeOutThird(ChangeOutputAttrType changeOutThird) {
        this.changeOutThird = changeOutThird;
    }

    public float getCommonWords() {
        return this.commonWords;
    }

    public void setCommonWords(float commonWords) {
        this.commonWords = commonWords;
    }

    public FigureType getFreqFigure() {
        return this.freqFigure;
    }

    public void setFreqFigure(FigureType freqFigure) {
        this.freqFigure = freqFigure;
    }

    public String getKwRefCorpus() {
        return this.kwRefCorpus;
    }

    public void setKwRefCorpus(String kwRefCorpus) {
        this.kwRefCorpus = kwRefCorpus;
    }

    public String getKwRefSubCorpus() {
        return this.kwRefSubCorpus;
    }

    public void setKwRefSubCorpus(String kwRefSubCorpus) {
        this.kwRefSubCorpus = kwRefSubCorpus;
    }

    public int getMaxFreq() {
        return this.maxFreq;
    }

    public void setMaxFreq(int maxFreq) {
        this.maxFreq = maxFreq;
    }

    public int getMinFreq() {
        return this.minFreq;
    }

    public void setMinFreq(int minFreq) {
        this.minFreq = minFreq;
    }

    public OutputType getOutputType() {
        return this.outputType;
    }

    public void setOutputType(OutputType outputType) {
        this.outputType = outputType;
    }

    public String getRegexp() {
        return this.regexp;
    }

    public void setRegexp(String regexp) {
        this.regexp = regexp;
    }

    public String getSearchAttribute() {
        return this.searchAttribute;
    }

    public void setSearchAttribute(String searchAttribute) {
        this.searchAttribute = searchAttribute;
    }

    public String getSubCorpus() {
        return this.subCorpus;
    }

    public void setSubCorpus(String subCorpus) {
        this.subCorpus = subCorpus;
    }

    public String getWhiteList() {
        return this.whiteList;
    }

    public void setWhiteList(String whiteList) {
        this.whiteList = whiteList;
    }

    public int getnGramFrom() {
        return this.nGramFrom;
    }

    public void setnGramFrom(int nGramFrom) {
        this.nGramFrom = nGramFrom;
    }

    public int getnGramTo() {
        return this.nGramTo;
    }

    public void setnGramTo(int nGramTo) {
        this.nGramTo = nGramTo;
    }

    public boolean isHideSubNGram() {
        return this.hideSubNGram;
    }

    public void setHideSubNGram(boolean hideSubNGram) {
        this.hideSubNGram = hideSubNGram;
    }

    public boolean isNonWords() {
        return this.nonWords;
    }

    public void setNonWords(boolean nonWords) {
        this.nonWords = nonWords;
    }

    public boolean isUseNGrams() {
        return this.useNGrams;
    }

    public void setUseNGrams(boolean useNGrams) {
        this.useNGrams = useNGrams;
    }

    public enum FigureType {
        HIT, DOC, ARF;

        public static boolean contain(String txt) {
            try {
                WordListRequest.FigureType.valueOf(txt);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    public enum OutputType {
        SIMPLE, KEYWORDS, CHANGE;

        public static boolean contain(String txt) {
            try {
                WordListRequest.OutputType.valueOf(txt);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    public enum ChangeOutputAttrType {
        WORD, TAG, LEMMA;

        public static boolean contain(String txt) {
            try {
                WordListRequest.ChangeOutputAttrType.valueOf(txt);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

}
