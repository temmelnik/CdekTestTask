package com.cdektesttask.dao;


import com.cdektesttask.model.Note;
import com.google.common.collect.ImmutableMap;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Implementation of {@link NoteDAO} via JDBC.
 */
@Repository
public class NoteDAOImpl implements NoteDAO {
    private static final String FIELD_ID = "id";
    private static final String FIELD_RECORD = "record";
    private static final String TABLE = "notes";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public NoteDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Note note) {
        String sql = "INSERT INTO " + TABLE + "(" + FIELD_RECORD + ") VALUES (:record)";
        jdbcTemplate.update(sql, ImmutableMap.of("record", note.getRecord()));
    }

    @Override
    public List<Note> findAll() {
        return jdbcTemplate.query("SELECT * FROM " + TABLE, this::createNote);
    }

    @Override
    public List<Note> findAll(String q) {
        if (q == null || q.isEmpty()) {
            return findAll();
        }

        String sql = "SELECT * FROM " + TABLE + " WHERE LOWER(" + FIELD_RECORD + ") LIKE LOWER(:q)";
        Long id = safeStringToLong(q);
        if (id != null) {
            sql += " OR " + FIELD_ID + " = :id";
        }

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("q", String.format("%%%s%%", q));
        params.addValue("id", id);

        return jdbcTemplate.query(sql, params, this::createNote);
    }

    private Note createNote(ResultSet rs, int rowNum) throws SQLException {
        Note note = new Note();
        note.setId(rs.getLong(FIELD_ID));
        note.setRecord(rs.getString(FIELD_RECORD));
        return note;
    }

    private Long safeStringToLong(String q) {
        try {
            return Long.parseLong(q);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
