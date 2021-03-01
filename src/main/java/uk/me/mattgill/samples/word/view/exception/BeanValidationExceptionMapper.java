package uk.me.mattgill.samples.word.view.exception;

import java.util.LinkedList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.Provider;

@Provider
public class BeanValidationExceptionMapper extends CustomExceptionMapper<ConstraintViolationException> {

    private static final String ERROR_MESSAGE = "Invalid object data";

    @Override
    protected ResponseBuilder buildResponse(ConstraintViolationException exception) {
        return Response.status(422)
                .entity(new ServiceError(exception.getClass(), ERROR_MESSAGE, prepareDetails(exception)));
    }

    private String[] prepareDetails(ConstraintViolationException exception) {
        List<String> details = new LinkedList<>();
        for (ConstraintViolation<?> cv : exception.getConstraintViolations()) {
            details.add(cv.getPropertyPath() + " " + cv.getMessage());
        }
        return details.toArray(new String[0]);
    }

}