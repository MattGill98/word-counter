package uk.me.mattgill.samples.word;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uk.me.mattgill.samples.word.model.WordRecorder;

public class WordCounterTest {

    private WordRecorder recorder;
    private WordCounter counter;

    @BeforeEach
    public void setup() {
        this.recorder = new WordRecorder();
        this.counter = new WordCounter(recorder);
    }

    @Test
    public void when_parsing_basic_sentence_expect_correct_result() {

        final String result = counter //
                .read("Hello world & good morning. The date is 18/05/2016") //
                .summarise();

        assertEquals("""
                Word count = 9
                Average word length = 4.556
                Number of words of length 1 is 1
                Number of words of length 2 is 1
                Number of words of length 3 is 1
                Number of words of length 4 is 2
                Number of words of length 5 is 2
                Number of words of length 7 is 1
                Number of words of length 10 is 1
                The most frequently occurring word length is 2, for word lengths of 4 & 5""", result);
    }

    @Test
    public void when_parsing_correct_uri_expect_result() throws URISyntaxException, IOException, InterruptedException {
        final URI testUri = new URI("https://baconipsum.com/api/?type=meat-and-filler&format=text");
        counter.read(testUri);
        assertTrue(recorder.getTotalCount() > 50, "The text wasn't correctly recorded");
    }

    @Test
    public void badUrlText() throws URISyntaxException, IOException, InterruptedException {
        final URI testUri = new URI("file://not-remote-request");
        assertThrows(IllegalArgumentException.class, () -> counter.read(testUri));
        assertEquals(0, recorder.getTotalCount(), "No words should have been recorded");
    }

}
