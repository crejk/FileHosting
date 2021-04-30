package com.crejk.filehosting.download;

import com.crejk.filehosting.file.FilePointer;
import com.crejk.filehosting.file.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.UUID;

public final class DownloadService {

    private static final Logger LOG = LoggerFactory.getLogger(DownloadService.class);

    private final FileService fileService;

    public DownloadService(FileService fileService) {
        this.fileService = fileService;
    }

    public Mono<ResponseEntity<Resource>> downloadFile(UUID id) {
        LOG.debug("File download for {}", id);

        return fileService.getFile(id)
                .map(this::filePointerAsResponseEntity);
    }

    private ResponseEntity<Resource> filePointerAsResponseEntity(FilePointer file) {
        LOG.debug("FilePointer name '{}', mediaType {}", file.getOriginalName(), file.getMediaType().getOrNull());

        return ResponseEntity.ok()
                .contentType(file.getMediaType().getOrNull())
                .contentLength(file.getSize())
                .header("Content-Disposition", "filename=" + file.getOriginalName())
                .body(new InputStreamResource(file.open()));
    }
}
