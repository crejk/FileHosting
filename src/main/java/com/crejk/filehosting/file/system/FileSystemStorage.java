package com.crejk.filehosting.file.system;

import com.crejk.filehosting.file.FileStorage;
import io.vavr.concurrent.Future;
import io.vavr.control.Option;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Set;
import java.util.concurrent.ExecutorService;

final class FileSystemStorage implements FileStorage {

    private static final Set<OpenOption> CREATE_AND_WRITE = Set.of(StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);

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

        return Option.some(path.toFile());
    }

    @Override
    public Future<Path> createFile(String filename, byte[] content) {
        var path = Path.of(directory.getAbsolutePath(), filename);

        try {
            var channel = AsynchronousFileChannel
                    .open(path, CREATE_AND_WRITE, executor);

            var future = channel.write(ByteBuffer.wrap(content), 0);

            return Future.fromJavaFuture(future).map(i -> path);
        } catch (IOException e) {
            return Future.failed(e);
        }
    }
}
