package org.freethinking.keyphrase;

public class Word {
    
    private String word;
    private String stemmedWord;
    private long position;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getStemmedWord() {
        return stemmedWord;
    }

    public void setStemmedWord(String stemmedWord) {
        this.stemmedWord = stemmedWord;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }
    

}
