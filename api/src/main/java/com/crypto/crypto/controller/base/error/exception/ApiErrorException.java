package com.crypto.crypto.controller.base.error.exception;

import com.crypto.crypto.errorHandling.ErrorHandlingConstants.ERROR_BUNDLE;

public class ApiErrorException extends RuntimeException {

    private static final long serialVersionUID = 1716973072265285147L;

    private ERROR_BUNDLE error;
    private String description;

    public ApiErrorException() {
        super();
    }

    public ApiErrorException(ERROR_BUNDLE error) {
        super(error.getDesc());
        this.error = error;
        this.description = error.getDesc();
    }

    public ApiErrorException(ERROR_BUNDLE error, String message) {
        super(message);
        this.error = error;
        this.description = error.getDesc();
    }

    public ApiErrorException(ERROR_BUNDLE error, String description, String message, Throwable t) {
        super(message, t);
        this.error = error;
        this.description = description;
    }

    public ERROR_BUNDLE getError() {
        return this.error;
    }

    public String getDescription() {
        return this.description;
    }
}