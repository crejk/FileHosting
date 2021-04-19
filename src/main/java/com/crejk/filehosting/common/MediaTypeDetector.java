package com.crejk.filehosting.common;

import io.vavr.control.Option;
import io.vavr.control.Try;
import org.springframework.http.MediaType;

import java.io.File;
import java.nio.file.Files;

public final class MediaTypeDetector {

    private MediaTypeDetector() {
    }

    public static Option<MediaType> detect(File file) {
        return Try.of(() -> Files.probeContentType(file.toPath()))
                .flatMap(contentType -> Try.of(() -> MediaType.parseMediaType(contentType)))
                .toOption();
    }
}
