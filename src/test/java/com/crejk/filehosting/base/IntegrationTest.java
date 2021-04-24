package com.crejk.filehosting.base;

import com.crejk.filehosting.FileHostingApplication;
import com.crejk.filehosting.file.FileService;
import com.crejk.filehosting.infrastructure.Profiles;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

@ActiveProfiles(profiles = Profiles.SYSTEM)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = FileHostingApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public abstract class IntegrationTest {

    @Autowired
    protected TestRestTemplate restTemplate;
    @Autowired
    protected WebApplicationContext webApplicationContext;
    @Autowired
    protected FileService fileService;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    protected MockMvc mockMvc;

    @BeforeEach
    public void setup() throws IOException {
        Path filesPath = Paths.get("files");

        if (!Files.exists(filesPath)) {
            Files.createDirectory(filesPath);
        }

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @AfterEach
    public void cleanup() throws IOException {
        jdbcTemplate.update("delete from files", Collections.emptyMap());

        FileUtils.deleteDirectory(new File("files"));
    }
}
