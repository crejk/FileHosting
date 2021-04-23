package com.crejk.filehosting.common;

import org.springframework.http.HttpStatus;

public final class RequestFailure {

    private final HttpStatus status;
    private final String message;

    public RequestFailure(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public RequestFailure(HttpStatus status) {
        this(status, status.getReasonPhrase());
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
