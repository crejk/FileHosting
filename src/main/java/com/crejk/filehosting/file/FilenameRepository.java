package com.crejk.filehosting.file;

import io.vavr.control.Option;

import java.util.UUID;

public interface FilenameRepository {

    Option<String> getOriginalFilename(UUID id);

    UUID saveOriginalFilename(UUID id, String name);
}
