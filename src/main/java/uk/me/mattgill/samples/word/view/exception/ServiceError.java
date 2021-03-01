package uk.me.mattgill.samples.word.view.exception;

import java.io.Serializable;

public class ServiceError implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String type;
    private final String message;
    private final String[] details;

    protected <T extends Throwable> ServiceError(Class<T> errorType, String message, String... details) {
        this.type = errorType.getSimpleName();
        this.message = message;
        this.details = details;
    }

    protected ServiceError(Throwable throwable) {
        this(throwable.getClass(), throwable.getMessage());
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public String[] getDetails() {
        return details;
    }

}