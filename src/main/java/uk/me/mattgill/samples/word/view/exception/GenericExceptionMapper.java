package uk.me.mattgill.samples.word.view.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper extends CustomExceptionMapper<Exception> {

    @Override
    protected ResponseBuilder buildResponse(Exception exception) {
        return Response.status(500).entity(new ServiceError(exception));
    }

}