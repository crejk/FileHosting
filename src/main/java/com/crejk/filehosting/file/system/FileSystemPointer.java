package com.crejk.filehosting.file.system;

import com.crejk.filehosting.file.FilePointer;
import com.google.common.base.MoreObjects;
import io.vavr.control.Option;
import org.springframework.http.MediaType;

import java.io.*;

final class FileSystemPointer implements FilePointer {

    private final File target;
    private final String originalName;
    private final Option<MediaType> mediaType;

    public FileSystemPointer(File target, String originalName, Option<MediaType> mediaType) {
        this.target = target;
        this.originalName = originalName;
        this.mediaType = mediaType;
    }

    @Override
    public InputStream open() {
        try {
            return new BufferedInputStream(new FileInputStream(target));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String getOriginalName() {
        return originalName;
    }

    @Override
    public long getSize() {
        return target.length();
    }

    @Override
    public Option<MediaType> getMediaType() {
        return mediaType;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("target", target)
                .add("name", originalName)
                .add("size", getSize())
                .add("mediaType", mediaType)
                .toString();
    }
}
