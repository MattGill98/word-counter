package uk.me.mattgill.samples.word.controller;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.InputStream;
import java.util.Scanner;

import uk.me.mattgill.samples.word.model.WordRecorder;

/**
 * A processor to read words from an InputStream using the UTF-8 charset.
 */
public class WordParser {

    private final WordRecorder recorder;

    /**
     * Construct a parser object
     * 
     * @param recorder the object used to record each word
     */
    public WordParser(WordRecorder recorder) {
        this.recorder = recorder;
    }

    /**
     * Parse the given InputStream, passing valid words to the word recorder
     * 
     * @param stream the InputStream to parse for words
     */
    public void parse(InputStream stream) {
        try (Scanner scanner = new Scanner(stream, UTF_8)) {
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