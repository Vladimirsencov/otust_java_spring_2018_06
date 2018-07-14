package ru.otus.hw05.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import ru.otus.hw05.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ru.otus.hw05.dao.DBConsts.*;

public class GenreRowMapper implements RowMapper<Genre> {
    @Nullable
    @Override
    public Genre mapRow(ResultSet rs, int i) throws SQLException {
        return new Genre(rs.getLong(F_ID), rs.getString(F_NAME));
    }
}
