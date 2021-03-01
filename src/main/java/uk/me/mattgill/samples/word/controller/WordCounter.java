package uk.me.mattgill.samples.word.controller;

import static java.lang.String.format;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class WordCounter {

    private List<String> words = new ArrayList<>();

    public WordCounter read(String source) {
        try (Scanner scanner = new Scanner(source)) {
            scanner.useDelimiter("\\s");
            while (scanner.hasNext()) {
                String word = scanner.next().replace(".", "");
                words.add(word);
            }
        }
        return this;
    }

    public String summarise() {
        final ByteArrayOutputStream result = new ByteArrayOutputStream();

        try (PrintWriter out = new PrintWriter(result)) {
            out.println("Word count = " + words.size());
            out.println(format("Average word length = %.3f",
                    words.stream().collect(Collectors.averagingInt(String::length))));
            Map<Integer, Integer> wordLengthMap = new HashMap<>();
            for (String word : words) {
                final int length = word.length();
                if (wordLengthMap.containsKey(length)) {
                    wordLengthMap.put(length, wordLengthMap.get(length) + 1);
                } else {
                    wordLengthMap.put(length, 1);
                }
            }
            wordLengthMap.entrySet().stream() //
                    .sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey())) //
                    .forEach(entry -> out
                            .println(format("Number of words of length %d is %d", entry.getKey(), entry.getValue())));
        }

        return result.toString();
    }

}
