package com.crejk.filehosting.file.system;

import com.crejk.filehosting.file.FilePointer;
import com.crejk.filehosting.file.FilePointerMapper;
import com.crejk.filehosting.file.FileRepository;
import com.crejk.filehosting.file.type.FileMediaTypeDetector;
import io.vavr.control.Option;

import java.io.File;
import java.util.UUID;

public final class FileSystemPointerMapper implements FilePointerMapper {

    private final FileRepository repository;
    private final FileMediaTypeDetector contentTypeDetector;

    public FileSystemPointerMapper(FileRepository repository, FileMediaTypeDetector contentTypeDetector) {
        this.repository = repository;
        this.contentTypeDetector = contentTypeDetector;
    }

    @Override
    public Option<FilePointer> from(UUID id, File file) {
        return repository.getOriginalFilename(id).map(originalFilename -> fileSystemPointerOf(file, originalFilename));
    }

    private FileSystemPointer fileSystemPointerOf(File file, String originalFilename) {
        return new FileSystemPointer(file, originalFilename, contentTypeDetector.detect(file));
    }
}
