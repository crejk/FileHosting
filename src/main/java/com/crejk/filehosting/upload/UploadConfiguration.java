package com.crejk.filehosting.upload;

import com.crejk.filehosting.file.FileService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UploadConfiguration {

    @Bean
    public UploadService uploadService(FileService fileService) {
        return new UploadService(fileService);
    }
}
