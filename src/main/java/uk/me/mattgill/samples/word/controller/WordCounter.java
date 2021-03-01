package uk.me.mattgill.samples.word.controller;

import java.util.Scanner;

import uk.me.mattgill.samples.word.model.WordRecorder;

public class WordCounter {

    private final WordRecorder recorder = new WordRecorder();

    public WordCounter read(String source) {
        try (Scanner scanner = new Scanner(source)) {
            scanner.useDelimiter("[\\s.,:;?!']");
            while (scanner.hasNext()) {
                String word = scanner.next().replace(".", "");
                if (!word.isEmpty()) {
                    recorder.record(word);
                }
            }
        }
        return this;
    }

    public String summarise() {
        return new ResultFormatter(recorder).formatAsText();
    }

}