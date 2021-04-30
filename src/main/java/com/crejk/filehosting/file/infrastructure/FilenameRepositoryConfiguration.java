package com.crejk.filehosting.file.infrastructure;

import com.crejk.filehosting.file.FilenameRepository;
import com.crejk.filehosting.infrastructure.Profiles;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.r2dbc.core.DatabaseClient;

@Profile(Profiles.SYSTEM)
@Configuration
public class FilenameRepositoryConfiguration {

    @Bean
    public FilenameRepository fileRepository(DatabaseClient databaseClient) {
        return new SQLFilenameRepository(databaseClient);
    }

    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new CompositeDatabasePopulator(
                new ResourceDatabasePopulator(new ClassPathResource("schema.sql"))));
        return initializer;
    }
}
