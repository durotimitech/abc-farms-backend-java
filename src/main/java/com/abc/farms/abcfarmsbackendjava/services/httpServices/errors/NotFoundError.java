package com.abc.farms.abcfarmsbackendjava.services.httpServices.errors;

public class NotFoundError extends Exception {
    public NotFoundError(String message) {
        super(message);
    }

    public NotFoundError(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundError(Throwable cause) {
        super(cause);
    }

    public NotFoundError() {
        super();
    }


}
