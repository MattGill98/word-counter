package uk.me.mattgill.samples.word.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * A client that creates InputStreams from various sources.
 */
public class TextClient {

    /**
     * @param text the String to create a stream from
     * @return an input stream representing the input String
     */
    public InputStream stream(String text) {
        return new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * @param uri the URI to fetch. This can be either a remote URI, or a path to a
     *            file
     * @return an input stream representing the result of fetching the given URI
     * 
     * @throws IllegalArgumentException if the given uri is not a valid HTTP/S
     *                                  address or file
     * @throws InterruptedException     if the request times out (after 10 seconds)
     * @throws IOException              if there is an error reading the response
     */
    public InputStream stream(URI uri) throws IOException, InterruptedException {
        final String scheme = uri.getScheme().toLowerCase();
        if (scheme.contains("file")) {
            return streamFile(new File(uri));
        }
        return streamUrl(uri);
    }

    private InputStream streamFile(File file) throws IOException {
        if (!file.exists()) {
            throw new IllegalArgumentException("File not found: " + file.getAbsolutePath());
        }
        return new BufferedInputStream(new FileInputStream(file));
    }

    private InputStream streamUrl(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(uri) //
                .GET() //
                .timeout(Duration.ofSeconds(10)) //
                .build();

        HttpResponse<InputStream> response = HttpClient.newBuilder() //
                .followRedirects(Redirect.ALWAYS) //
                .build() //
                .send(request, BodyHandlers.ofInputStream());

        final int statusCode = response.statusCode();
        if (statusCode > 299 || statusCode < 200) {
            throw new IOException("Invalid response code from URL: " + statusCode);
        }

        return response.body();
    }

}
