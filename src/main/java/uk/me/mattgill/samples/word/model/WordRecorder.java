package uk.me.mattgill.samples.word.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class WordRecorder {

    private final List<String> words = new ArrayList<>();
    private final Map<Integer, AtomicInteger> lengths = new HashMap<>();

    public void record(String word) {
        words.add(word);

        final int length = word.length();
        if (lengths.containsKey(length)) {
            lengths.get(length).incrementAndGet();
        } else {
            lengths.put(length, new AtomicInteger(1));
        }
    }

    public int getTotalCount() {
        return words.size();
    }

    public double getAverageWordLength() {
        return words.stream().collect(Collectors.averagingInt(String::length));
    }

    public Iterable<Integer> getWordLengths() {
        return lengths.keySet().stream().sorted().collect(Collectors.toList());
    }

    public Integer getWordsOfLength(int wordLength) {
        if (!lengths.containsKey(wordLength)) {
            return null;
        }
        return lengths.get(wordLength).get();
    }

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
