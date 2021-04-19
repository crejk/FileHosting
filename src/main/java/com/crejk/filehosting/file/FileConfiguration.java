package com.crejk.filehosting.file;

import com.crejk.filehosting.file.type.FileMediaTypeDetector;
import com.crejk.filehosting.file.type.TikaFileMediaTypeDetector;
import org.apache.tika.Tika;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileConfiguration {

    @Bean
    public FileMediaTypeDetector contentTypeFetcher() {
        return new TikaFileMediaTypeDetector(new Tika());
    }

    @Bean
    public FileService fileService(FileStorage storage, FileRepository repository, FilePointerMapper mapper) {
        return new FileService(storage, repository, mapper);
    }
}
