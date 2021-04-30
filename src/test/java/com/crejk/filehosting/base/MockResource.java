package com.crejk.filehosting.base;

import com.crejk.filehosting.file.api.FileDto;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import reactor.core.publisher.Flux;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MockResource extends AbstractResource {

    private final String filename;
    private final byte[] content;

    public MockResource(String filename, byte[] content) {
        this.filename = filename;
        this.content = content;
    }

    public FileDto toFileDto() {
        return new FileDto(filename, Flux.just(new DefaultDataBufferFactory().wrap(content)));
    }

    @Override
    public String getFilename() {
        return filename;
    }

    public byte[] getContent() {
        return content;
    }

    @Override
    public String getDescription() {
        return "MockResource";
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(this.content);
    }
}
