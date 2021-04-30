package com.crejk.filehosting.file;

import reactor.core.publisher.Mono;

import java.util.UUID;

public interface FilenameRepository {

    Mono<String> getOriginalFilename(UUID id);

    Mono<UUID> saveOriginalFilename(UUID id, String name);
}
