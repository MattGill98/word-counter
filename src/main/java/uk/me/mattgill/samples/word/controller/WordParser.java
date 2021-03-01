package uk.me.mattgill.samples.word.controller;

import java.io.InputStream;
import java.util.Scanner;

import uk.me.mattgill.samples.word.model.WordRecorder;

public class WordParser {

    private final WordRecorder recorder;

    public WordParser(WordRecorder recorder) {
        this.recorder = recorder;
    }

    public void parse(InputStream stream) {
        try (Scanner scanner = new Scanner(stream)) {
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