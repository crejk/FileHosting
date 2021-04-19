package com.crejk.filehosting.upload;

import com.crejk.filehosting.common.RequestFailure;
import com.crejk.filehosting.file.FileService;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public final class UploadService {

    private static final Logger LOG = LoggerFactory.getLogger(UploadService.class);

    private final FileService fileService;

    public UploadService(FileService fileService) {
        this.fileService = fileService;
    }

    public ResponseEntity<UUID> uploadFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        LOG.debug("File upload for '{}'", originalFilename);

        return Option.of(originalFilename)
                .filter(originalName -> !originalName.isEmpty())
                .toEither(RequestFailure.of(HttpStatus.BAD_REQUEST))
                .map(originalName -> createFile(file, originalName).get())
                .fold(
                        failure -> ResponseEntity.status(failure.getCode()).build(),
                        ResponseEntity::ok
                );
    }

    private Try<UUID> createFile(MultipartFile file, String originalFilename) {
        return Try.of(file::getBytes)
                .flatMap(content -> fileService.createFile(originalFilename, content).toTry());
    }
}
