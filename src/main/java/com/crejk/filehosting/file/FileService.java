package com.crejk.filehosting.file;

import com.crejk.filehosting.common.RequestFailure;
import io.vavr.concurrent.Future;
import io.vavr.control.Either;

import java.util.UUID;

public interface FileService {

    Either<RequestFailure, FilePointer> getFile(UUID id);

    Future<UUID> createFile(String originalFilename, byte[] content);
}
