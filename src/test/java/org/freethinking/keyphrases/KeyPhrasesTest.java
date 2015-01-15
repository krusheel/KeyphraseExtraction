package org.freethinking.keyphrases;

import java.util.Map;

import org.freethinking.keyphrase.KeyPhraseStats;
import org.freethinking.keyphrase.KeyPhrases;
import org.junit.Assert;
import org.junit.Test;

public class KeyPhrasesTest {

    private KeyPhrases keyPhrases = new KeyPhrases();

    @Test
    public void substitutePunctuationsTest() {
        String inputString = "This is a market-place. It's supposed to be busy.";
        String expectedOutputString = "This is a market place PUNCT_MARKER It s supposed to be busy PUNCT_MARKER ";

        String substitutedString = keyPhrases.substitutePunctuations(
                inputString, "PUNCT_MARKER");

        Assert.assertEquals("Punctuation substitution is not done",
                expectedOutputString, substitutedString);
    }

    @Test
    public void keyPhrasesWithStatsTest() {

        String inputString = "This is a market-place. It's supposed to be busy.";

        String cleanInput = keyPhrases.substitutePunctuations(
                inputString, "PUNCT_MARKER");
        Map<String, KeyPhraseStats> keyPhraseList = keyPhrases.extractKeyPhrasesWithStats(cleanInput);

        for (Map.Entry<String, KeyPhraseStats> keyPhrase : keyPhraseList.entrySet()) {
            System.out.format("%-13s, Count: %-4d, Position: %-2d %n", keyPhrase.getKey(),
                    keyPhrase.getValue().getCount(), keyPhrase.getValue().getPosition());

        }

    }

}
