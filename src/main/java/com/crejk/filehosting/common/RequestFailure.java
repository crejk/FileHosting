package com.crejk.filehosting.common;

import org.springframework.http.HttpStatus;

public class RequestFailure {

    private final int code;
    private final String message;

    public RequestFailure(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static RequestFailure of(HttpStatus status) {
        return new RequestFailure(status.value(), status.getReasonPhrase());
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
