package com.crejk.filehosting.file.type;

import io.vavr.control.Option;
import io.vavr.control.Try;
import org.apache.tika.Tika;
import org.springframework.http.MediaType;

import java.io.File;

public final class TikaFileMediaTypeDetector implements FileMediaTypeDetector {

    private final Tika tika;

    public TikaFileMediaTypeDetector(Tika tika) {
        this.tika = tika;
    }

    @Override
    public Option<MediaType> detect(File file) {
        return Try.of(() -> tika.detect(file))
                .flatMap(contentType -> Try.of(() -> MediaType.parseMediaType(contentType)))
                .toOption();
    }
}
