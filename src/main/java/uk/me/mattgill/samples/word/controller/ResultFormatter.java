package uk.me.mattgill.samples.word.controller;

import java.util.ArrayList;
import java.util.List;

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

        return String.join(System.lineSeparator(), lines);
    }

}
