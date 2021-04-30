package com.crejk.filehosting.common;

import io.vavr.control.Option;
import io.vavr.control.Try;
import org.apache.tika.Tika;
import org.springframework.http.MediaType;

import java.io.File;

public final class MediaTypeDetector {

    private static final Tika tika = new Tika();

    private MediaTypeDetector() {
    }

    public static Option<MediaType> detect(File file) {
        return Try.of(() -> tika.detect(file))
                .flatMap(contentType -> Try.of(() -> MediaType.parseMediaType(contentType)))
                .toOption();
    }
}
