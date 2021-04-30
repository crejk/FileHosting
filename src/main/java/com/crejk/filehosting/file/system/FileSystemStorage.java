package com.crejk.filehosting.file.system;

import com.crejk.filehosting.file.api.FileDto;
import com.crejk.filehosting.file.FileStorage;
import io.vavr.control.Option;
import org.springframework.core.io.buffer.DataBufferUtils;
import reactor.core.publisher.Mono;

import java.io.File;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.*;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public final class FileSystemStorage implements FileStorage {

    private static final Set<OpenOption> CREATE_AND_WRITE = Set.of(StandardOpenOption.CREATE, StandardOpenOption.WRITE);

    private final File directory;
    private final ExecutorService executor;

    public FileSystemStorage(File directory, ExecutorService executor) {
        this.directory = directory;
        this.executor = executor;
    }

    @Override
    public Option<File> findFile(String filename) {
        var path = Path.of(directory.getAbsolutePath(), filename);

        if (!Files.exists(path)) {
            return Option.none();
        }

        return Option.of(path.toFile());
    }

    @Override
    public Mono<Path> createFile(String name, FileDto fileDto) {
        var path = Paths.get(directory.getAbsolutePath(), name);

        return Mono.fromCallable(() -> AsynchronousFileChannel.open(path, CREATE_AND_WRITE, executor))
                .flatMapMany(channel -> DataBufferUtils.write(fileDto.getContent(), channel, 0))
                .then(Mono.just(path));
    }
}
