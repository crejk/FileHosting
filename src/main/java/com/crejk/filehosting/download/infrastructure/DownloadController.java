package com.crejk.filehosting.download.infrastructure;

import com.crejk.filehosting.download.DownloadService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
public final class DownloadController {

    private final DownloadService downloadService;

    public DownloadController(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @GetMapping(
            path = "/download/{id}"
    )
    public Mono<ResponseEntity<Resource>> download(@PathVariable UUID id) {
        return downloadService.downloadFile(id);
    }
}
