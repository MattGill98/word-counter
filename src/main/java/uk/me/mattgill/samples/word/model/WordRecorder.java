package uk.me.mattgill.samples.word.model;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * An object to store various statistics from recorded words. Note that this
 * class is not Thread safe, as it is assumed that a new object will be used for
 * each thread.
 */
public class WordRecorder {

    private int wordCount = 0;
    private int wordLengthTotal = 0;

    private int highestLengthFrequency = 0;
    private final Set<Integer> highestLengthFrequencySet = new TreeSet<>();

    private final Map<Integer, AtomicInteger> lengths = new TreeMap<>();

    public void record(String word) {
        if (word == null) {
            throw new IllegalArgumentException("Recorded word cannot be null");
        }

        final int wordLength = word.length();

        // Increment the number of recorded words, and increase the total
        wordCount++;
        wordLengthTotal += wordLength;

        // Increment the count of the length of the passes word
        int frequency = lengths.computeIfAbsent(wordLength, i1 -> new AtomicInteger(0)) //
                .incrementAndGet();

        if (frequency >= highestLengthFrequency) {
            if (frequency != highestLengthFrequency) {
                highestLengthFrequencySet.clear();
            }
            highestLengthFrequency = frequency;
            highestLengthFrequencySet.add(wordLength);
        }
    }

    public int getTotalCount() {
        return wordCount;
    }

    public double getAverageWordLength() {
        return (double) wordLengthTotal / wordCount;
    }

    public Collection<Integer> getWordLengths() {
        return lengths.keySet();
    }

    public int getWordsOfLength(int wordLength) {
        return lengths.getOrDefault(wordLength, new AtomicInteger(0)).get();
    }

    public Collection<Integer> getHighestFrequencyWordLengths() {
        return highestLengthFrequencySet;
    }

}
