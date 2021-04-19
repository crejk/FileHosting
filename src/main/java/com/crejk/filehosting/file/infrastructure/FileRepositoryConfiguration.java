package com.crejk.filehosting.file.infrastructure;

import com.crejk.filehosting.file.FileRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class FileRepositoryConfiguration {

    @Bean
    public FileRepository fileRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        return new SQLFileRepository(jdbcTemplate);
    }
}
