package uk.me.mattgill.samples.word;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import uk.me.mattgill.samples.word.controller.ResultFormatter;
import uk.me.mattgill.samples.word.controller.TextClient;
import uk.me.mattgill.samples.word.controller.WordParser;
import uk.me.mattgill.samples.word.model.WordRecorder;

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

    public WordCounter read(String source) {
        final InputStream stream = client.stream(source);
        parser.parse(stream);
        return this;
    }

    public WordCounter read(URI source) throws IOException, InterruptedException {
        InputStream stream;
        stream = client.stream(source);
        parser.parse(stream);
        return this;
    }

    public String summarise() {
        return formatter.formatAsText();
    }

}
