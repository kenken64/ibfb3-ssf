package sg.edu.nus.iss.app.corpusAnalyzer.model;

import java.io.Serializable;

public class Corpus implements Serializable{
    private String word;
    private String nextWord;
    private Integer count;

    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    public String getNextWord() {
        return nextWord;
    }
    public void setNextWord(String nextWord) {
        this.nextWord = nextWord;
    }
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
}
