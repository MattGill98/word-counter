package uk.me.mattgill.samples.word;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import uk.me.mattgill.samples.word.controller.ResultFormatter;
import uk.me.mattgill.samples.word.controller.TextClient;
import uk.me.mattgill.samples.word.controller.WordParser;
import uk.me.mattgill.samples.word.model.WordRecorder;

public class WordCounter {

    private static final Logger LOGGER = Logger.getLogger(WordCounter.class.getName());

    private final WordRecorder recorder;
    private final ResultFormatter formatter;
    private final WordParser parser;
    private final TextClient client;

    public WordCounter() {
        this.recorder = new WordRecorder();
        this.formatter = new ResultFormatter(recorder);
        this.parser = new WordParser(recorder);
        this.client = new TextClient();
    }

    public WordCounter read(String source) {
        final InputStream stream = client.stream(source);
        parser.parse(stream);
        return this;
    }

    public WordCounter read(URI source) {
        InputStream stream;
        try {
            stream = client.stream(source);
            parser.parse(stream);
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, ex, () -> "Failed to read from uri " + source);
        } catch (InterruptedException ex) {
            LOGGER.log(Level.WARNING, ex, () -> "Request timeout to uri " + source);
        }
        return this;
    }

    public String summarise() {
        return formatter.formatAsText();
    }

}
