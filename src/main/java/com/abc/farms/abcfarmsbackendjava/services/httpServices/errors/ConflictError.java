package com.abc.farms.abcfarmsbackendjava.services.httpServices.errors;

public class ConflictError extends Exception {
    public ConflictError(String message) {
        super(message);
    }

    public ConflictError(String message, Throwable cause) {
        super(message, cause);
    }

    public ConflictError(Throwable cause) {
        super(cause);
    }

    public ConflictError() {
        super();
    }
}
