package uk.me.mattgill.samples.word.view.model;

import java.net.URI;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class CountWordsObject {

    @Schema(type = SchemaType.STRING, format = "http[s]?://.+")
    private URI url;

    private String text;

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
