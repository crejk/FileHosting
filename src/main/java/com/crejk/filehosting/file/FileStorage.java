package com.crejk.filehosting.file;

import io.vavr.concurrent.Future;
import io.vavr.control.Option;

import java.io.File;

public interface FileStorage {

    Option<File> findFile(String filename);

    Future<File> createFile(String filename, byte[] content);
}
