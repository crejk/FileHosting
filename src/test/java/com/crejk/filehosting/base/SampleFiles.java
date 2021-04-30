package com.crejk.filehosting.base;

import java.nio.charset.StandardCharsets;

public final class SampleFiles {

    public static MockResource TEXT_FILE = new MockResource("file.txt", "abc".getBytes(StandardCharsets.UTF_8));

    private SampleFiles() {
    }
}
