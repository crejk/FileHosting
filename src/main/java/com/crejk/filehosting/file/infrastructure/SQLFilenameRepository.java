package com.crejk.filehosting.file.infrastructure;

import com.crejk.filehosting.file.FilenameRepository;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

final class SQLFilenameRepository implements FilenameRepository {

    private static final String INSERT_FILE_QUERY = "insert into files (id, name) VALUES (:id, :name)";
    private static final String FILENAME_BY_ID_QUERY = "select name from files where (files.id = :id)";

    private final DatabaseClient databaseClient;

    SQLFilenameRepository(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Override
    public Mono<String> getOriginalFilename(UUID id) {
        return databaseClient.sql(FILENAME_BY_ID_QUERY)
                .bind("id", id)
                .fetch()
                .one()
                .map(result -> result.get("name").toString());
    }

    @Override
    public Mono<UUID> saveOriginalFilename(UUID id, String name) {
        return databaseClient.sql(INSERT_FILE_QUERY)
                .bind("id", id)
                .bind("name", name)
                .then().thenReturn(id);
    }
}
