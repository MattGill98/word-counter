package uk.me.mattgill.samples.word.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Scanner;

import uk.me.mattgill.samples.word.model.WordRecorder;

public class WordParser {

    private final WordRecorder recorder;

    public WordParser(WordRecorder recorder) {
        this.recorder = recorder;
    }

    public void parse(String source) {
        try (Scanner scanner = new Scanner(source)) {
            process(scanner);
        }
    }

    public void parse(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(uri) //
                .GET() //
                .timeout(Duration.ofSeconds(10)) //
                .build();

        HttpResponse<InputStream> response = HttpClient.newHttpClient() //
                .send(request, BodyHandlers.ofInputStream());

        try (Scanner scanner = new Scanner(response.body())) {
            process(scanner);
        }
    }

    private void process(Scanner scanner) {
        scanner.useDelimiter("[\\s.,?!;:]");
        while (scanner.hasNext()) {
            String word = scanner.next();
            if (!word.isEmpty()) {
                recorder.record(word);
            }
        }
    }

}