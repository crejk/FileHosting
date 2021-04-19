package com.crejk.filehosting.file.infrastructure;

import com.crejk.filehosting.file.FilenameRepository;
import io.vavr.control.Option;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.UUID;

final class SQLFilenameRepository implements FilenameRepository {

    private static final String INSERT_FILE_QUERY = "insert into files (id, name) VALUES (:id, :name)";
    private static final String FILENAME_BY_ID_QUERY = "select name from files where (files.id = :id)";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public SQLFilenameRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Option<String> getOriginalFilename(UUID id) {
        try {
            String filename = jdbcTemplate.queryForObject(FILENAME_BY_ID_QUERY, Map.of("id", bytesFromUUID(id)), String.class);

            return Option.some(filename);
        } catch (EmptyResultDataAccessException e) {
            return Option.none();
        }
    }

    @Override
    public UUID saveOriginalFilename(UUID id, String name) {
        jdbcTemplate.update(INSERT_FILE_QUERY, Map.of("id", bytesFromUUID(id), "name", name));

        return id;
    }

    private static byte[] bytesFromUUID(UUID id) {
        var buffer = ByteBuffer.wrap(new byte[16]);

        buffer.putLong(id.getMostSignificantBits());
        buffer.putLong(id.getLeastSignificantBits());

        return buffer.array();
    }
}
