package com.crejk.filehosting.download;

import com.crejk.filehosting.common.RequestFailure;
import com.crejk.filehosting.file.FilePointer;
import com.crejk.filehosting.file.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public final class DownloadService {

    private final FileService fileService;

    public DownloadService(FileService fileService) {
        this.fileService = fileService;
    }

    public ResponseEntity<Resource> downloadFile(UUID id) {
        return fileService.getFile(id).fold(this::errorAsResponseEntity, this::filePointerAsResponseEntity);
    }

    private ResponseEntity<Resource> filePointerAsResponseEntity(FilePointer file) {
        return ResponseEntity.ok()
                .contentType(file.getMediaType().getOrNull())
                .contentLength(file.getSize())
                .header("Content-Disposition", "inline; filename=" + file.getOriginalName())
                .body(new InputStreamResource(file.open()));
    }

    private ResponseEntity<Resource> errorAsResponseEntity(RequestFailure left) {
        return ResponseEntity.status(left.getStatus())
                .body(new ByteArrayResource(left.getMessage().getBytes(StandardCharsets.UTF_8)));
    }
}
