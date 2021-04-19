package com.crejk.filehosting.file;

import io.vavr.concurrent.Future;
import io.vavr.control.Option;

import java.io.File;
import java.nio.file.Path;

public interface FileStorage {

    Option<File> findFile(String filename);

    Future<Path> createFile(String filename, byte[] content);
}
