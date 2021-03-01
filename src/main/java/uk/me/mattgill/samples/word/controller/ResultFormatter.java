package uk.me.mattgill.samples.word.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import uk.me.mattgill.samples.word.model.WordRecorder;

public class ResultFormatter {

    private final WordRecorder recorder;

    public ResultFormatter(WordRecorder recorder) {
        this.recorder = recorder;
    }

    public String formatAsText() {

        final List<String> lines = new ArrayList<>();

        lines.add("Word count = " + recorder.getTotalCount());

        lines.add(String.format("Average word length = %.3f", recorder.getAverageWordLength()));

        for (int wordLength : recorder.getWordLengths()) {
            lines.add(String.format( //
                    "Number of words of length %d is %d", //
                    wordLength, //
                    recorder.getWordsOfLength(wordLength) //
            ));
        }

        final List<Integer> highestFrequencyWordLengths = recorder.getHighestFrequencyWordLengths();
        final int highestWordLengthFrequency = recorder.getWordsOfLength(highestFrequencyWordLengths.get(0));
        lines.add(String.format("The most frequently occurring word length is %d, for word lengths of %s",
                highestWordLengthFrequency, formatList(highestFrequencyWordLengths)));

        return String.join(System.lineSeparator(), lines);
    }

    private static String formatList(Collection<?> items) {
        if (items.isEmpty()) {
            return "";
        }
        final String result = String.join(", ", items.stream().map(Object::toString).collect(Collectors.toList()));

        if (items.size() < 2) {
            return result;
        }

        final int lastCommaIndex = result.lastIndexOf(",");
        return result.substring(0, lastCommaIndex) + " &" + result.substring(lastCommaIndex + 1);
    }

}
