package com.crypto.crypto.controller.base.error;

import com.crypto.crypto.errorHandling.ErrorHandlingConstants.ERROR_BUNDLE;

public class ApiErrorResponse {
    private String error;
    private String description;

    public ApiErrorResponse() {
    }

    public ApiErrorResponse(ERROR_BUNDLE error) {
        this(error.name(), error.getDesc());
    }

    public ApiErrorResponse(ERROR_BUNDLE error, String description) {
        this(error.name(), description);
    }

    public ApiErrorResponse(String error, String description) {
        this.setError(error);
        this.setDescription(description);
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}