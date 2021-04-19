package com.crejk.filehosting.file.infrastructure;

import com.crejk.filehosting.file.FilenameRepository;
import com.crejk.filehosting.infrastructure.Profiles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Profile(Profiles.SYSTEM)
@Configuration
public class FilenameRepositoryConfiguration {

    @Bean
    public FilenameRepository fileRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        return new SQLFilenameRepository(jdbcTemplate);
    }
}
