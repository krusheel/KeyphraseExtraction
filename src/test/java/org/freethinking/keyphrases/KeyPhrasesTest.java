package org.freethinking.keyphrases;

import java.util.Map;

import org.freethinking.keyphrase.KeyPhrases;
import org.junit.Assert;
import org.junit.Test;

public class KeyPhrasesTest {

    @Test
    public void substitutePunctuationsTest() {
        String inputString = "This is a market-place. It's supposed to be busy.";
        String expectedOutputString = "This is a market place PUNCT_MARKER It s supposed to be busy PUNCT_MARKER ";

        String substitutedString = KeyPhrases.substitutePunctuations(
                inputString, "PUNCT_MARKER");

        Assert.assertEquals("Punctuation substitution is not done",
                expectedOutputString, substitutedString);
    }

    @Test
    public void keyPhrasesWithStemmingTest() {
        String inputString = "This is a market-place. It's supposed to be busy.";
        String expectedOutputString = "This is a market place PUNCT_MARKER It s suppose to be busy PUNCT_MARKER ";

        String cleanInput = KeyPhrases.substitutePunctuations(
                inputString, "PUNCT_MARKER");
        Map<String, Long> keyPhraseList = KeyPhrases.extractKeyPhrasesWithCount(cleanInput);

        System.out.println(keyPhraseList);

        Assert.assertEquals("key phrases not matched",
                5, keyPhraseList.size());
    }

}
