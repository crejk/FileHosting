package com.crejk.filehosting;

import com.crejk.filehosting.base.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;

import static com.crejk.filehosting.base.SampleFiles.TEXT_FILE;

public class UploadTest extends IntegrationTest {

    @Test
    public void shouldReturnIdOfUploadedFile() {
        // given
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("file", TEXT_FILE);

        // when
        var result = webTestClient.post()
                .uri("/upload/")
                .bodyValue(bodyBuilder.build())
                .exchange();

        // then
        result.expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);
    }
}
