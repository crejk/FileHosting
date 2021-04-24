package com.crejk.filehosting;

import com.crejk.filehosting.base.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.crejk.filehosting.base.SampleFiles.TEXT_FILE;
import static com.crejk.filehosting.base.SampleFiles.TEXT_FILE_WITHOUT_ORIGINAL_FILENAME;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UploadTest extends IntegrationTest {

    @Test
    public void shouldReturnIdOfUploadedFile() throws Exception {
        // given
        var file = TEXT_FILE;

        // when
        var result = mockMvc.perform(multipart("/upload").file(file));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldReturnBadRequestErrorIfOriginalFilenameIsNull() throws Exception {
        // given
        var file = TEXT_FILE_WITHOUT_ORIGINAL_FILENAME;

        // when
        var result = mockMvc.perform(multipart("/upload").file(file));

        // then
        result.andExpect(status().isBadRequest());
    }
}
