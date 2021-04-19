package com.crejk.filehosting.upload.infrastructure;

import com.crejk.filehosting.upload.UploadService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
public final class UploadController {

    private final UploadService service;

    public UploadController(UploadService service) {
        this.service = service;
    }

    @PostMapping(
            path = "/upload",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UUID> upload(@RequestParam MultipartFile file) {
        return service.uploadFile(file);
    }
}
