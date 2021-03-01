package uk.me.mattgill.samples.word.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import uk.me.mattgill.samples.word.model.WordRecorder;

/**
 * A formatter to format the results from a {@link WordRecorder} for convenient
 * viewing.
 */
public class ResultFormatter {

    private final WordRecorder recorder;

    public ResultFormatter(WordRecorder recorder) {
        this.recorder = recorder;
    }

    /**
     * @return a text string describing the key features of the stored recorder data
     */
    public String formatAsText() {

        final int wordCount = recorder.getTotalCount();
        final String wordCountLine = "Word count = " + wordCount;

        // Print default message if no words are found
        if (wordCount == 0) {
            return wordCountLine;
        }

        final List<String> lines = new ArrayList<>();
        lines.add(wordCountLine);

        lines.add(String.format("Average word length = %.3f", recorder.getAverageWordLength()));

        for (int wordLength : recorder.getWordLengths()) {
            lines.add(String.format( //
                    "Number of words of length %d is %d", //
                    wordLength, //
                    recorder.getWordsOfLength(wordLength) //
            ));
        }

        final var highestFrequencyWordLengths = recorder.getHighestFrequencyWordLengths();
        final int highestWordLengthFrequency = recorder.getWordsOfLength(highestFrequencyWordLengths.iterator().next());
        lines.add(String.format("The most frequently occurring word length is %d, for word lengths of %s",
                highestWordLengthFrequency, formatList(highestFrequencyWordLengths)));

        return String.join(System.lineSeparator(), lines);
    }

    /**
     * @param items a list of serializable items
     * @return a string representing the list, formatted for a readable sentence
     */
    protected static String formatList(Collection<? extends Serializable> items) {
        if (items.isEmpty()) {
            return "";
        }

        // Format the list of serialized items separated by a comma
        final String result = String.join(", ", items.stream() //
                .map(Object::toString) //
                .collect(Collectors.toList()));

        // For single items, return early
        if (items.size() < 2) {
            return result;
        }

        // Any larger lists, the last comma should be an ampersand
        final int lastCommaIndex = result.lastIndexOf(",");
        return result.substring(0, lastCommaIndex) + " &" + result.substring(lastCommaIndex + 1);
    }

}
