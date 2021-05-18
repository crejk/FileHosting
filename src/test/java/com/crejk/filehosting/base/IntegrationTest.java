package com.crejk.filehosting.base;

import com.crejk.filehosting.FileHostingApplication;
import com.crejk.filehosting.file.FileService;
import com.crejk.filehosting.infrastructure.Profiles;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ActiveProfiles(profiles = Profiles.SYSTEM)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = FileHostingApplication.class)
@TestPropertySource(locations = "classpath:application-test.yml")
@Testcontainers
@AutoConfigureWebTestClient
public abstract class IntegrationTest {

    @Container
    static PostgreSQLContainer container = new PostgreSQLContainer("postgres:13.1")
            .withDatabaseName("files");
    @Autowired
    protected FileService fileService;
    @Autowired
    private WebTestClient webTestClient;
    protected TestClient testClient;
    @Autowired
    private DatabaseClient databaseClient;

    @DynamicPropertySource
    static void propertySource(DynamicPropertyRegistry registry) {
        String jdbcUrl = container.getJdbcUrl();

        registry.add("spring.r2dbc.url", () -> jdbcUrl.replace("jdbc", "r2dbc"));
        registry.add("spring.r2dbc.username", container::getUsername);
        registry.add("spring.r2dbc.password", container::getPassword);
    }

    @BeforeEach
    public void setup() throws IOException {
        testClient = new TestClient(webTestClient);
        Path filesPath = Paths.get("files");

        if (!Files.exists(filesPath)) {
            Files.createDirectory(filesPath);
        }
    }

    @AfterEach
    public void cleanup() throws IOException {
        databaseClient
                .sql("delete from files")
                .then()
                .subscribe();

        FileUtils.deleteDirectory(new File("files"));
    }
}
