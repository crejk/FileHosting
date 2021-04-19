package com.crejk.filehosting.file.system;

import com.crejk.filehosting.file.FileStorage;
import io.vavr.concurrent.Future;
import io.vavr.control.Option;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public final class FileSystemStorage implements FileStorage {

    private static final Set<OpenOption> CREATE_AND_WRITE = Set.of(StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);

    private final File directory;
    private final ExecutorService executor;

    public FileSystemStorage(File directory, ExecutorService executor) {
        this.directory = directory;
        this.executor = executor;
    }

    @Override
    public Option<File> findFile(String filename) {
        return listFiles(nameFilter(filename))
                .filter(files -> files.length >= 1)
                .map(files -> files[0]);
    }

    @Override
    public Future<File> createFile(String filename, byte[] content) {
        var file = new File(directory, filename);

        try {
            var channel = AsynchronousFileChannel
                    .open(file.toPath(), CREATE_AND_WRITE, executor);

            var future = channel.write(ByteBuffer.wrap(content), 0);

            return Future.fromJavaFuture(future).map(i -> file);
        } catch (IOException e) {
            return Future.failed(e);
        }
    }

    private Option<File[]> listFiles(FilenameFilter filenameFilter) {
        return Option.of(directory.listFiles(filenameFilter));
    }

    private FilenameFilter nameFilter(String name) {
        return (dir, filename) -> filename.equals(name);
    }
}
