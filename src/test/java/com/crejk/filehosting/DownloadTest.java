package com.crejk.filehosting;

import com.crejk.filehosting.base.IntegrationTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.crejk.filehosting.base.SampleFiles.TEXT_FILE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DownloadTest extends IntegrationTest {

    @Test
    public void shouldReturnFile() throws Exception {
        // given
        UUID fileId = fileService.createFile(TEXT_FILE.getOriginalFilename(), TEXT_FILE.getBytes()).get();

        // when
        var result = mockMvc.perform(get("/download/" + fileId));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().bytes(TEXT_FILE.getBytes()))
                .andExpect(header().string("Content-Disposition", "filename=" + TEXT_FILE.getOriginalFilename()));
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
