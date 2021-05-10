package com.crejk.filehosting.file.system;

import com.crejk.filehosting.common.MediaTypeDetector;
import com.crejk.filehosting.file.FilePointer;
import com.crejk.filehosting.file.FileService;
import com.crejk.filehosting.file.FileStorage;
import com.crejk.filehosting.file.FilenameRepository;
import com.crejk.filehosting.file.api.FileDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

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
    public Mono<FilePointer> getFile(UUID id) {
        return storage.findFile(id.toString())
                .map(Mono::just).getOrElse(Mono::empty)
                .flatMap(file -> repository.getOriginalFilename(id)
                        .map(originalFilename -> fileSystemPointerOf(file, originalFilename)))
                .switchIfEmpty(Mono.error(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    private FilePointer fileSystemPointerOf(File file, String originalFilename) {
        return new FileSystemPointer(file, originalFilename, MediaTypeDetector.detect(file));
    }

    @Override
    public Mono<UUID> createFile(FileDto fileDto) {
        var id = UUID.randomUUID();

        return storage.createFile(id.toString(), fileDto)
                .flatMap(x -> repository.saveOriginalFilename(id, fileDto.getFilename()));
    }
}
