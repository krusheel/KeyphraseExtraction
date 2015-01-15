package org.freethinking.keyphrase;

import java.util.Map;

public class KeyPhraseStats {

    private long count;
    private long position;
    private Map<String, Long> rawPhrases;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public Map<String, Long> getRawPhrases() {
        return rawPhrases;
    }

    public void setRawPhrases(Map<String, Long> rawPhrases) {
        this.rawPhrases = rawPhrases;
    }

}
