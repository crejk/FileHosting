package com.crejk.filehosting.file.type;

import io.vavr.control.Option;
import org.springframework.http.MediaType;

import java.io.File;

public interface FileMediaTypeDetector {

    Option<MediaType> detect(File file);
}
