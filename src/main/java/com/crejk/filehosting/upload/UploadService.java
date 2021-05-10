package com.crejk.filehosting.upload;

import com.crejk.filehosting.file.FileService;
import com.crejk.filehosting.file.api.FileDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.UUID;

public final class UploadService {

    private static final Logger LOG = LoggerFactory.getLogger(UploadService.class);

    private final FileService fileService;

    public UploadService(FileService fileService) {
        this.fileService = fileService;
    }

    public Mono<UUID> uploadFile(FilePart file) {
        String originalFilename = file.filename();
        LOG.debug("File upload for '{}'", originalFilename);

        return Mono.justOrEmpty(originalFilename)
                .filter(originalName -> !originalName.isEmpty())
                .switchIfEmpty(Mono.error(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Original filename cannot be empty!")))
                .flatMap(originalName -> createFile(file));
    }

    private Mono<UUID> createFile(FilePart filePart) {
        return fileService.createFile(FileDto.of(filePart))
                .doOnError(cause -> LOG.error("Failed to create file '" + filePart.filename() + "'", cause))
                .switchIfEmpty(Mono.error(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)));
    }
}
