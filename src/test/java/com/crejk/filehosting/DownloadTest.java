package com.crejk.filehosting;

import com.crejk.filehosting.base.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.UUID;

import static com.crejk.filehosting.base.SampleFiles.TEXT_FILE;

public class DownloadTest extends IntegrationTest {

    @Test
    public void shouldReturnFile() {
        // given
        UUID fileId = fileService.createFile(TEXT_FILE.toFileDto()).block();

        // when
        var result = webTestClient.get()
                .uri("/download/" + fileId)
                .exchange();

        // then
        result.expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_PLAIN)
                .expectHeader().valueEquals("Content-Disposition", "filename=" + TEXT_FILE.getFilename())
                .expectBody(byte[].class).isEqualTo(TEXT_FILE.getContent());
    }

    @Test
    public void shouldReturnNotFoundErrorIfUserWantToDownloadNonexistentFile() {
        // given
        UUID nonexistentFileId = UUID.randomUUID();

        // when
        var result = webTestClient.get()
                .uri("/download/" + nonexistentFileId)
                .exchange();

        // then
        result.expectStatus().isNotFound();
    }
}
