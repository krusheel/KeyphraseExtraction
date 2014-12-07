package org.freethinking.keyphrase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyPhrases {

	private static String[] excludeList = { "this", "is", "a", "s", "it", "to",
			"be", "punct_marker" };

	public static String substitutePunctuations(String text, String replacement) {
		String substitutedString;

		substitutedString = text.replaceAll("[,.]", " " + replacement + " ");
		substitutedString = substitutedString.replaceAll("-|\\s+|'", " ");
		return substitutedString;
	}

	public static List<String> extractUniGrams(String text) {

		List<String> excludeList = Arrays.asList(KeyPhrases.excludeList);

		List<String> unigrams = Arrays.asList(text.split(" "));

		List<String> cleanedUniGrams = new ArrayList<String>();
		for (String unigram : unigrams) {
			if (!excludeList.contains(unigram.toLowerCase())) {
				cleanedUniGrams.add(unigram);
			}
		}

		return cleanedUniGrams;
	}

	public static List<String> extractBiGrams(String text) {

		List<String> unigrams = Arrays.asList(text.split(" "));
		List<String> bigrams = new ArrayList<String>();

		List<String> excludeList = Arrays.asList(KeyPhrases.excludeList);
		String prev = "";
		for (String unigram : unigrams) {

			if (excludeList.contains(unigram.toLowerCase())) {
				prev = "";
				continue;
			}

			if (!prev.isEmpty()) {
				bigrams.add(prev + " " + unigram);
			}
			prev = unigram;
		}

		return bigrams;
	}

	public static List<String> extractUniGramsAndBiGrams(String text) {

		List<String> excludeList = Arrays.asList(KeyPhrases.excludeList);

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

	public static List<String> extractKeyPhrases(String text) {

		List<String> excludeList = Arrays.asList(KeyPhrases.excludeList);

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

	public static Map<String, Long> extractKeyPhrasesWithCount(String text) {

		List<String> excludeList = Arrays.asList(KeyPhrases.excludeList);
		List<String> unigrams = Arrays.asList(text.split(" "));
		Map<String, Long> keyPhrases = new HashMap<String, Long>();

		String prev = "";
		for (String unigram : unigrams) {

			if (excludeList.contains(unigram.toLowerCase())) {

				prev = "";
			} else {

				updateKeyPhrases(unigram, keyPhrases);
				if (!prev.isEmpty()) {

					String currBiGram = prev + " " + unigram;
					updateKeyPhrases(currBiGram, keyPhrases);
				}
				prev = unigram;
			}
		}

		return keyPhrases;
	}

	/*
	 * Updates KeyPhrase Count if it exists Adds if it doesn't exist
	 */
	private static void updateKeyPhrases(String phrase,
			Map<String, Long> keyPhrases) {

		Long one = (long) 1;

		if (keyPhrases.containsKey(phrase)) {
			keyPhrases.put(phrase, keyPhrases.get(phrase) + 1);
		} else {
			keyPhrases.put(phrase, one);
		}
	}

	public static void main(String[] args) {

		String input = "This is a market-place. It's supposed to be busy.";

		String cleanInput = KeyPhrases.substitutePunctuations(input,
				"PUNCT_MARKER");

		/*
		 * List<String> unigrams = KeyPhrases.extractUniGrams(cleanInput); for
		 * (String unigram : unigrams) { System.out.println(unigram); }
		 */

		/*
		 * List<String> bigrams = KeyPhrases.extractBiGrams(cleanInput); for
		 * (String bigram : bigrams) { System.out.println(bigram); }
		 */

		/*
		 * List<String> uniGramsAndBiGrams = KeyPhrases
		 * .extractUniGramsAndBiGrams(cleanInput); for (String uniGramAndBiGram
		 * : uniGramsAndBiGrams) { System.out.println(uniGramAndBiGram); }
		 */

		/*
		 * List<String> keyPhrases = KeyPhrases.extractKeyPhrases(cleanInput);
		 * for (String keyPhrase : keyPhrases) { System.out.println(keyPhrase);
		 * }
		 */

		Map<String, Long> keyPhrasesWithCount = KeyPhrases
				.extractKeyPhrasesWithCount(cleanInput);
		for (Map.Entry<String, Long> keyPhrase : keyPhrasesWithCount.entrySet()) {
			System.out.format("%-13s Count: %-4d %n", keyPhrase.getKey(),
					keyPhrase.getValue());
		}

	}

}
