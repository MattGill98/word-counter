package uk.me.mattgill.samples.word;

import uk.me.mattgill.samples.word.controller.ResultFormatter;
import uk.me.mattgill.samples.word.controller.WordParser;
import uk.me.mattgill.samples.word.model.WordRecorder;

public class WordCounter {

    private final WordRecorder recorder;
    private final ResultFormatter formatter;
    private final WordParser parser;

    public WordCounter() {
        this.recorder = new WordRecorder();
        this.formatter = new ResultFormatter(recorder);
        this.parser = new WordParser(recorder);
    }

    public WordCounter read(String source) {
        parser.parse(source);
        return this;
    }

    public String summarise() {
        return formatter.formatAsText();
    }

}
