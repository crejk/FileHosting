package com.crejk.filehosting.file.system;

import com.crejk.filehosting.file.FileService;
import com.crejk.filehosting.file.FileStorage;
import com.crejk.filehosting.file.FilenameRepository;
import com.crejk.filehosting.infrastructure.Profiles;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Profile(Profiles.SYSTEM)
@Configuration
public class FileSystemConfiguration {

    @Bean
    public FileService fileService(FileStorage storage, FilenameRepository repository) {
        return new FileSystemService(storage, repository);
    }

    @Bean
    public FileStorage fileStorage(File filesDirectory, ExecutorService filesTaskExecutor) {
        return new FileSystemStorage(filesDirectory, filesTaskExecutor);
    }

    @Bean
    public File filesDirectory(@Value("${files.directory}") String directoryName) throws IOException {
        File file = new File(directoryName);

        if (!file.exists()) {
            Files.createDirectory(Path.of(directoryName));
        }

        if (!file.isDirectory()) {
            throw new RuntimeException("File '" + directoryName + "' isn't a directory!");
        }

        return file;
    }

    @Bean
    public ExecutorService filesTaskExecutor() {
        return Executors.newCachedThreadPool(
                new ThreadFactoryBuilder()
                        .setNameFormat("Files-%d")
                        .setDaemon(false)
                        .build()
        );
    }
}
