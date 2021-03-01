package uk.me.mattgill.samples.word.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class TextClient {

    public InputStream stream(String text) {
        return new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
    }

    public InputStream stream(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(uri) //
                .GET() //
                .timeout(Duration.ofSeconds(10)) //
                .build();

        HttpResponse<InputStream> response = HttpClient.newBuilder() //
                .followRedirects(Redirect.ALWAYS) //
                .build() //
                .send(request, BodyHandlers.ofInputStream());

        return response.body();
    }

}
