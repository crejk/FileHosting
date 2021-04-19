package com.crejk.filehosting;

import com.crejk.filehosting.base.IntegrationTest;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DownloadTest extends IntegrationTest {

    @Test
    public void shouldReturnFile() throws Exception {
        // given
        byte[] fileContent = "test".getBytes(StandardCharsets.UTF_8);
        UUID fileId = fileService.createFile("test", fileContent).get();

        // when
        var result = mockMvc.perform(get("/download/" + fileId));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().bytes(fileContent));
    }

    @Test
    public void shouldReturnNotFoundErrorIfUserWantToDownloadNonexistentFile() throws Exception {
        // given
        UUID nonexistentFileId = UUID.randomUUID();

        // when
        var result = mockMvc.perform(get("/download/" + nonexistentFileId));

        // then
        result.andExpect(status().isNotFound());
    }
}
