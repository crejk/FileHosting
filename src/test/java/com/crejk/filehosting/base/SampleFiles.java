package com.crejk.filehosting.base;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;

public final class SampleFiles {

    public static final MockMultipartFile TEXT_FILE = creatTextFile("file", "file.txt", "abc");

    public static final MockMultipartFile TEXT_FILE_WITHOUT_ORIGINAL_FILENAME = creatTextFile("file", null, "abc");

    private static MockMultipartFile creatTextFile(String name, String originalFilename, String content) {
        return new MockMultipartFile(name, originalFilename, MediaType.TEXT_PLAIN_VALUE, content.getBytes(StandardCharsets.UTF_8));
    }

    private SampleFiles() {
    }
}
