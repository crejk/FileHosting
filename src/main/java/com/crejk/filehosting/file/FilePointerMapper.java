package com.crejk.filehosting.file;

import io.vavr.control.Option;

import java.io.File;
import java.util.UUID;

public interface FilePointerMapper {

     Option<FilePointer> from(UUID id, File file);
}
