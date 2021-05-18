package com.crejk.filehosting.base;

import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

public class TestClient {

    private final WebTestClient webTestClient;

    public TestClient(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    public WebTestClient.ResponseSpec download(UUID fileId) {
        return webTestClient.get()
                .uri("/download/" + fileId)
                .exchange();
    }

    public WebTestClient.ResponseSpec upload(Object body) {
        return webTestClient.post()
                .uri("/upload/")
                .bodyValue(body)
                .exchange();
    }
}
