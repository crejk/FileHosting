package com.crejk.filehosting.file;

import com.crejk.filehosting.file.api.FileDto;
import io.vavr.control.Option;
import reactor.core.publisher.Mono;

import java.io.File;
import java.nio.file.Path;

public interface FileStorage {

    Option<File> findFile(String filename);

    Mono<Path> createFile(String name, FileDto fileDto);
}
