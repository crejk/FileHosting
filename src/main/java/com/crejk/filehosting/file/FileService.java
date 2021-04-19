package com.crejk.filehosting.file;

import com.crejk.filehosting.common.RequestFailure;
import io.vavr.concurrent.Future;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public final class FileService {

    private final FileStorage storage;
    private final FileRepository repository;
    private final FilePointerMapper mapper;

    public FileService(FileStorage storage, FileRepository repository, FilePointerMapper mapper) {
        this.storage = storage;
        this.repository = repository;
        this.mapper = mapper;
    }

    public Either<RequestFailure, FilePointer> getFile(UUID id) {
        return storage.findFile(id.toString())
                .flatMap(file -> mapper.from(id, file))
                .toEither(RequestFailure.of(HttpStatus.NOT_FOUND));
    }

    public Future<UUID> createFile(String originalFilename, byte[] content) {
        var id = UUID.randomUUID();

        return storage.createFile(id.toString(), content)
                .map(x -> repository.saveOriginalFilename(id, originalFilename));
    }
}
