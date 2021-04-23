package com.crejk.filehosting.common;

import io.vavr.control.Either;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

public final class ResponseEntityConverter {

    private ResponseEntityConverter() {
    }

    public static  <T> ResponseEntity<T> toResponseEntity(Either<RequestFailure, T> either) {
        return either
                .map(ResponseEntity::ok)
                .getOrElseThrow(failure -> new ResponseStatusException(failure.getStatus(), failure.getMessage()));
    }
}
