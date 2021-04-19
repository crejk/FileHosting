package com.crejk.filehosting;

import com.crejk.filehosting.base.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UploadTest extends IntegrationTest {

    @Test
    public void shouldReturnIdOfUploadedFile() throws Exception {
        // given
        var file = new MockMultipartFile(
                "file", "file.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "abcd".getBytes(StandardCharsets.UTF_8)
        );

        // when
        var result = mockMvc.perform(multipart("/upload").file(file));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldReturnBadRequestErrorIfOriginalFilenameIsNull() throws Exception {
        // given
        var file = new MockMultipartFile(
                "file", null,
                MediaType.TEXT_PLAIN_VALUE,
                "abcd".getBytes(StandardCharsets.UTF_8)
        );

        // when
        var result = mockMvc.perform(multipart("/upload").file(file));

        // then
        result.andExpect(status().isBadRequest());
    }
}
