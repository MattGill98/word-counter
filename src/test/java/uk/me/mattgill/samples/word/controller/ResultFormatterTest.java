package uk.me.mattgill.samples.word.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uk.me.mattgill.samples.word.model.WordRecorder;

public class ResultFormatterTest {

    WordRecorder recorder = new WordRecorder();
    ResultFormatter formatter = new ResultFormatter(recorder);

    @BeforeEach
    public void setup() {
        recorder = new WordRecorder();
        formatter = new ResultFormatter(recorder);
    }

    @Test
    public void when_no_words_parsed_expect_default_message() {
        assertEquals("Word count = 0", formatter.formatAsText());
    }

    @Test
    public void when_one_word_parsed_expect_correct_format() {
        recorder.record("Hello");

        final String result = formatter.formatAsText();
        assertLinesPresent(result, //
                "Word count = ", //
                "Average word length = ", //
                "Number of words of length 5 is ", //
                "The most frequently occurring word length is " //
        );
        // Check that there are no leading or trailing slashes
        assertEquals(result, result.trim());
    }

    @Test
    public void when_3_word_lengths_expect_correct_order() {
        recorder.record("Hello");
        recorder.record("Matthew");
        recorder.record("Gill");

        final String result = formatter.formatAsText();
        assertLinesPresent(result, //
                "Number of words of length 4 is ", //
                "Number of words of length 5 is ", //
                "Number of words of length 7 is " //
        );
    }

    @Test
    public void when_format_list_with_size_0_expect_empty_string() {
        assertEquals("", ResultFormatter.formatList(List.of()));
    }

    @Test
    public void when_format_list_with_size_1_expect_no_extra_formatting() {
        assertEquals("apple", ResultFormatter.formatList(List.of("apple")));
    }

    @Test
    public void when_format_list_with_size_2_expect_ampersand_separator() {
        assertEquals("apple & bananas", ResultFormatter.formatList(List.of("apple", "bananas")));
    }

    @Test
    public void when_format_list_with_size_3_expect_comma_and_ampersand_separator() {
        assertEquals("apple, bananas & pears", ResultFormatter.formatList(List.of("apple", "bananas", "pears")));
    }

    @Test
    public void when_format_list_with_size_4_expect_comma_and_ampersand_separator() {
        assertEquals("apple, bananas, pineapples & pears",
                ResultFormatter.formatList(List.of("apple", "bananas", "pineapples", "pears")));
    }

    private void assertLinesPresent(String text, String... searchTerms) {

        int lastIndex = -1;
        for (String searchTerm : searchTerms) {
            final int index = text.indexOf(searchTerm, lastIndex);
            assertNotEquals(-1, index, String.format("""
                    Search term not found or in wrong order.
                    expected: '%s'.
                    actual: '%s'""", searchTerm, text));
            lastIndex = index;
        }
    }

}
