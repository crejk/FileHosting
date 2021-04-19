package com.crejk.filehosting.download;

import com.crejk.filehosting.file.FileService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DownloadConfiguration {

    @Bean
    public DownloadService downloadService(FileService fileService) {
        return new DownloadService(fileService);
    }
}
