package uk.me.mattgill.samples.word;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import uk.me.mattgill.samples.word.controller.ResultFormatter;
import uk.me.mattgill.samples.word.controller.TextClient;
import uk.me.mattgill.samples.word.controller.WordParser;
import uk.me.mattgill.samples.word.model.WordRecorder;

/**
 * A fluent API for parsing words from an input source and summarising the
 * results.
 */
public class WordCounter {

    private final ResultFormatter formatter;
    private final WordParser parser;
    private final TextClient client;

    public WordCounter() {
        this(new WordRecorder());
    }

    public WordCounter(WordRecorder recorder) {
        this.formatter = new ResultFormatter(recorder);
        this.parser = new WordParser(recorder);
        this.client = new TextClient();
    }

    /**
     * Parse the words from the given string
     * 
     * @param source a string to parse
     */
    public WordCounter read(String source) {
        final InputStream stream = client.stream(source);
        parser.parse(stream);
        return this;
    }

    /**
     * Parse the words fetched from the given URI. Note that if the URI returns
     * HTML, the HTML will be parsed. This request will timeout after 10 seconds.
     * 
     * @param source the URI to fetch.
     * 
     * @throws IOException          if an error occurs while fetching the uri
     * @throws InterruptedException if the request is interrupted, or 10 seconds
     *                              elapses before a response.
     */
    public WordCounter read(URI source) throws IOException, InterruptedException {
        InputStream stream;
        stream = client.stream(source);
        parser.parse(stream);
        return this;
    }

    /**
     * @return a string summarising the recorded words.
     * 
     * @see ResultFormatter
     */
    public String summarise() {
        return formatter.formatAsText();
    }

}
