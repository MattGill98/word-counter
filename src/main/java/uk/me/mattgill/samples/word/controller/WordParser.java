package uk.me.mattgill.samples.word.controller;

import java.util.Scanner;

import uk.me.mattgill.samples.word.model.WordRecorder;

public class WordParser {

    private final WordRecorder recorder;

    public WordParser(WordRecorder recorder) {
        this.recorder = recorder;
    }

    public void read(String source) {
        try (Scanner scanner = new Scanner(source)) {
            scanner.useDelimiter("[\\s.,?!;:]");
            while (scanner.hasNext()) {
                String word = scanner.next();
                if (!word.isEmpty()) {
                    recorder.record(word);
                }
            }
        }
    }

}