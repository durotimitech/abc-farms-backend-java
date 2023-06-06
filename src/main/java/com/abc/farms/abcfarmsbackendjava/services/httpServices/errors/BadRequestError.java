package com.abc.farms.abcfarmsbackendjava.services.httpServices.errors;

public class BadRequestError extends RuntimeException {
    public BadRequestError(String message) {
        super(message);
    }

    public BadRequestError(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestError(Throwable cause) {
        super(cause);
    }

    public BadRequestError() {
        super();
    }

}
