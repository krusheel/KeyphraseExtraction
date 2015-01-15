package org.freethinking.keyphrase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyPhrases {

    private static String[] EXCLUDE_LIST = { "this", "that", "it", "i", "we",
            "is", "be", "s", "a", "to", "punct_marker", "am", "and" };
    private List<String> excludeList;

    private PorterStemmer porterStemmer = new PorterStemmer();

    public KeyPhrases() {
        excludeList = Arrays.asList(KeyPhrases.EXCLUDE_LIST);
    }

    public String substitutePunctuations(String text, String replacement) {
        String substitutedString;

        substitutedString = text.replaceAll("[,.]", " " + replacement + " ");
        substitutedString = substitutedString.replaceAll("-|\\s+|'", " ");
        return substitutedString;
    }

    public List<String> extractKeyPhrases(String text) {

        List<String> unigrams = Arrays.asList(text.split(" "));

        List<String> keyPhrases = new ArrayList<String>();
        String prev = "";
        for (String unigram : unigrams) {
            if (excludeList.contains(unigram.toLowerCase())) {
                prev = "";
                continue;
            }

            keyPhrases.add(unigram);
            if (!prev.isEmpty()) {
                keyPhrases.add(prev + " " + unigram);
            }
            prev = unigram;
        }
        return keyPhrases;
    }

    /*
     * Updates KeyPhrase Count if it exists Adds if it doesn't exist
     */
    private void updateKeyPhrases(String prevWord, String currWord,
            Map<String, Long> keyPhrases) {

        Long one = (long) 1;

        String currStemmedWord;
        if (currWord != null) {
            currStemmedWord = stem(currWord);
        } else {
            return;
        }

        String phrase;
        if (prevWord != null) {
            String prevStemmedWord = stem(prevWord);
            phrase = prevStemmedWord + " " + currStemmedWord;
        } else {
            phrase = currStemmedWord;
        }

        if (keyPhrases.containsKey(phrase)) {
            keyPhrases.put(phrase, keyPhrases.get(phrase) + 1);
        } else {
            keyPhrases.put(phrase, one);
        }
    }

    public Map<String, Long> extractKeyPhrasesWithCount(String text) {

        List<String> unigrams = Arrays.asList(text.split(" "));
        Map<String, Long> keyPhrases = new HashMap<String, Long>();

        String prev = "";
        for (String unigram : unigrams) {

            if (excludeList.contains(unigram.toLowerCase())) {

                prev = "";
            } else {

                updateKeyPhrases(null, unigram, keyPhrases);
                if (!prev.isEmpty()) {

                    updateKeyPhrases(prev, unigram, keyPhrases);
                }
                prev = unigram;
            }
        }

        return keyPhrases;
    }

    private String stem(String word) {

        porterStemmer.add(word.toCharArray(), word.length());
        porterStemmer.stem();

        return porterStemmer.toString();
    }

    public Map<String, KeyPhraseStats> extractKeyPhrasesWithStats(String text) {

        List<String> unigrams = Arrays.asList(text.split(" "));
        Map<String, KeyPhraseStats> keyPhrases = new HashMap<String, KeyPhraseStats>();

        Word prevWord = null;
        long position = 0;
        for (String unigram : unigrams) {

            Word currWord = new Word();
            currWord.setWord(unigram);
            currWord.setPosition(++position);

            if (excludeList.contains(unigram.toLowerCase())) {
                prevWord = null;
            } else {

                updateKeyPhrases(null, currWord, keyPhrases);
                if (prevWord != null) {
                    updateKeyPhrases(prevWord, currWord, keyPhrases);
                }
                prevWord = currWord;
            }
        }

        return keyPhrases;
    }

    private Map<String, KeyPhraseStats> updateKeyPhrases(Word prevWord, Word currWord,
            Map<String, KeyPhraseStats> keyPhrases) {

        long one = (long ) 1;
        String phrase, rawPhrase;
        long position;

        if (null != currWord) {
            currWord.setStemmedWord(stem(currWord.getWord()));
            phrase = currWord.getStemmedWord();
            rawPhrase = currWord.getWord();
            position = currWord.getPosition();
        }
        else
            return keyPhrases;

        if (null != prevWord) {
            prevWord.setStemmedWord(stem(prevWord.getWord()));
            phrase = prevWord.getStemmedWord() + " " + currWord.getStemmedWord();
            rawPhrase = prevWord.getWord() + " " + currWord.getWord();
            position = prevWord.getPosition();
        }

        KeyPhraseStats keyPhraseStats;
        if (keyPhrases.containsKey(phrase)) {
            keyPhraseStats = keyPhrases.get(phrase);
            keyPhraseStats.setCount(keyPhraseStats.getCount() + one);
            keyPhraseStats.setPosition(Math.min(position, keyPhraseStats.getPosition()));            
        }
        else {
            keyPhraseStats = new KeyPhraseStats();
            keyPhraseStats.setCount(one);
            keyPhraseStats.setPosition(position);            
        }
        keyPhrases.put(phrase, keyPhraseStats);

        return keyPhrases;
    }

    public static void main(String[] args) {

        String input = "That is a market-place. It's supposed to be busy. And I am still going to market-place";

        KeyPhrases keyPhrases = new KeyPhrases();
        String cleanInput = keyPhrases.substitutePunctuations(input,
                "PUNCT_MARKER");

        /*
         * List<String> keyPhrases = keyPhrases.extractKeyPhrases(cleanInput);
         * for (String keyPhrase : keyPhrases) { System.out.println(keyPhrase);
         * }
         */

        Map<String, Long> keyPhrasesWithCount = keyPhrases
                .extractKeyPhrasesWithCount(cleanInput);
        for (Map.Entry<String, Long> keyPhrase : keyPhrasesWithCount.entrySet()) {
            System.out.format("%-13s Count: %-4d %n", keyPhrase.getKey(),
                    keyPhrase.getValue());
        }

    }

}
