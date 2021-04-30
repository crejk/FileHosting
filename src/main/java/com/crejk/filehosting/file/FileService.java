package com.crejk.filehosting.file;

import com.crejk.filehosting.file.api.FileDto;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface FileService {

    Mono<FilePointer> getFile(UUID id);

    Mono<UUID> createFile(FileDto fileDto);
}
