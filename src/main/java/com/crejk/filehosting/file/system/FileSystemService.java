package com.crejk.filehosting.file.system;

import com.crejk.filehosting.common.MediaTypeDetector;
import com.crejk.filehosting.common.RequestFailure;
import com.crejk.filehosting.file.FilePointer;
import com.crejk.filehosting.file.FileService;
import com.crejk.filehosting.file.FileStorage;
import com.crejk.filehosting.file.FilenameRepository;
import io.vavr.concurrent.Future;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.util.UUID;

final class FileSystemService implements FileService {

    private final FileStorage storage;
    private final FilenameRepository repository;

    public FileSystemService(FileStorage storage, FilenameRepository repository) {
        this.storage = storage;
        this.repository = repository;
    }

    @Override
    public Either<RequestFailure, FilePointer> getFile(UUID id) {
        return storage.findFile(id.toString())
                .flatMap(file -> repository.getOriginalFilename(id)
                        .map(originalFilename -> fileSystemPointerOf(file, originalFilename)))
                .toEither(new RequestFailure(HttpStatus.NOT_FOUND));
    }

    private FilePointer fileSystemPointerOf(File file, String originalFilename) {
        return new FileSystemPointer(file, originalFilename, MediaTypeDetector.detect(file));
    }

    @Override
    public Future<UUID> createFile(String originalFilename, byte[] content) {
        var id = UUID.randomUUID();

        return storage.createFile(id.toString(), content)
                .map(x -> repository.saveOriginalFilename(id, originalFilename));
    }
}
