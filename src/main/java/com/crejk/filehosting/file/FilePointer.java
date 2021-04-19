package com.crejk.filehosting.file;

import io.vavr.control.Option;
import org.springframework.http.MediaType;

import java.io.InputStream;

public interface FilePointer {

    InputStream open();

    String getOriginalName();

    long getSize();

    Option<MediaType> getMediaType();
}
