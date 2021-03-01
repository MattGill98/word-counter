package uk.me.mattgill.samples.word.view;

import java.net.URI;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import uk.me.mattgill.samples.word.WordCounter;
import uk.me.mattgill.samples.word.view.model.CountWordsObject;

@Path("word")
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class WordEndpoint {

    private WordCounter counter = new WordCounter();

    @POST
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countWords(CountWordsObject data) {
        final String text = data.getText();
        final URI url = data.getUrl();

        if (text != null) {
            counter.read(text);
        }
        if (url != null) {
            counter.read(url);
        }

        return counter.summarise();
    }

}
