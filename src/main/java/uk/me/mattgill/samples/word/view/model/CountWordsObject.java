package uk.me.mattgill.samples.word.view.model;

import java.net.URI;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class CountWordsObject {

    @Schema(type = SchemaType.STRING, format = "(file|http[s]?):\\/\\/.+", example = "http://janelwashere.com/files/bible_daily.txt")
    private URI uri;

    private String text;

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
