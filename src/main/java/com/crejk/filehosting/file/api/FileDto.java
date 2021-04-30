package com.crejk.filehosting.file.api;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;

import java.util.Objects;

public class FileDto {

    private final String filename;
    private final Flux<DataBuffer> content;

    public FileDto(String filename, Flux<DataBuffer> content) {
        this.filename = filename;
        this.content = content;
    }

    public static FileDto of(FilePart filePart) {
        return new FileDto(filePart.filename(), filePart.content());
    }

    public String getFilename() {
        return filename;
    }

    public Flux<DataBuffer> getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileDto fileDto = (FileDto) o;
        return Objects.equals(filename, fileDto.filename) && Objects.equals(content, fileDto.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filename, content);
    }
}
