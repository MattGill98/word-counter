package uk.me.mattgill.samples.word.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uk.me.mattgill.samples.word.model.WordRecorder;

public class WordParserTest {

    private WordRecorder recorder;
    private WordParser parser;

    @BeforeEach
    public void setup() {
        this.recorder = new WordRecorder();
        this.parser = new WordParser(recorder);
    }

    @Test
    public void when_simple_words_expect_recorded() {
        parser.read("Hello World");
        testRecordedWordCount(2);
    }

    /**
     * Test that valid word delimiters are used to separate words, by putting these
     * characters in between normal word characters. The definition of a valid
     * delimiter has been deduced from the example text as well as Wikipedia.
     * 
     * @see https://en.wikipedia.org/wiki/Word_count
     * @see uk.me.mattgill.samples.word.WordCounterTest WordCounterTest
     */
    @Test
    public void when_delimiter_found_expect_separate_words() {
        String[] delimiters = { //
                " ", //
                "\t", //
                ",", //
                ".", //
                "?", //
                "!", //
                ";", //
                ":", //
        };
        parser.read("start" + String.join("a", delimiters) + "end");
        testRecordedWordCount(delimiters.length + 1);
    }

    /**
     * Test for characters that may be construed as delimiters but aren't defined as
     * such, by adding them all in the middle of a string and ensuring only one word
     * is found.
     * 
     * @see #when_delimiter_found_expect_separate_words delimiter definition
     */
    @Test
    public void when_no_delimiter_found_expect_same_word() {
        char[] nonDelimiters = { //
                '/', //
                '\\', //
                '\'', //
                '`', //
                '~', //
        };
        parser.read("start" + new String(nonDelimiters) + "end");
        testRecordedWordCount(1);
    }

    @Test
    public void when_non_delimiter_symbols_found_in_word_expect_word_length_includes_them() {
        parser.read("Matthew'");
        testRecordedWordLength(8);
    }

    @Test
    public void when_delimiter_symbols_found_in_word_expect_word_length_excludes_them() {
        parser.read("Matthew!");
        testRecordedWordLength(7);
    }

    //// Utility methods

    private void testRecordedWordCount(int count) {
        assertEquals(count, recorder.getTotalCount(), "An incorrect number of words were found");
    }

    private void testRecordedWordLength(int length) {
        assertEquals(length, recorder.getWordLengths().iterator().next(),
                "The length of the parsed word was incorrect");
    }
}
