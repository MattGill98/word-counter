package uk.me.mattgill.samples.word.view.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.Provider;

@Provider
public class IllegalArgumentExceptionMapper extends CustomExceptionMapper<IllegalArgumentException> {

    @Override
    protected ResponseBuilder buildResponse(IllegalArgumentException exception) {
        return Response.status(422).entity(new ServiceError(exception));
    }

}