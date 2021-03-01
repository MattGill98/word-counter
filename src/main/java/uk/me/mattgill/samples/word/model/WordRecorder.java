package uk.me.mattgill.samples.word.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * An object to store various statistics from recorded words.
 */
public class WordRecorder {

    private final AtomicInteger wordCount = new AtomicInteger(0);
    private final AtomicInteger wordLengthTotal = new AtomicInteger(0);

    private final Map<Integer, AtomicInteger> lengths = new ConcurrentHashMap<>();

    public void record(String word) {
        if (word == null) {
            throw new IllegalArgumentException("Recorded word cannot be null");
        }

        wordCount.incrementAndGet();
        wordLengthTotal.addAndGet(word.length());

        // Increment the count of the length of the passes word
        lengths.computeIfAbsent(word.length(), wordLength -> new AtomicInteger(0)) //
                .incrementAndGet();
    }

    public int getTotalCount() {
        return wordCount.get();
    }

    public double getAverageWordLength() {
        return wordLengthTotal.doubleValue() / wordCount.get();
    }

    public Collection<Integer> getWordLengths() {
        return new LinkedHashSet<>(lengths.keySet());
    }

    public int getWordsOfLength(int wordLength) {
        return lengths.getOrDefault(wordLength, new AtomicInteger(0)).get();
    }

    // TODO: optimise
    public List<Integer> getHighestFrequencyWordLengths() {

        final List<Integer> resultList = new ArrayList<>();
        int highestFrequency = 0;

        for (Entry<Integer, AtomicInteger> lengthEntry : lengths.entrySet()) {
            final int frequency = lengthEntry.getValue().get();
            if (frequency > highestFrequency) {
                resultList.clear();
            }
            if (frequency >= highestFrequency) {
                resultList.add(lengthEntry.getKey());
                highestFrequency = frequency;
            }
        }

        return resultList;
    }

}
