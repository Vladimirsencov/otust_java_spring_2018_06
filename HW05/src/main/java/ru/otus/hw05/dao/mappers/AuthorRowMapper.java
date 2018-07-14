package ru.otus.hw05.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import ru.otus.hw05.models.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ru.otus.hw05.dao.DBConsts.*;

public class AuthorRowMapper implements RowMapper<Author> {
    @Nullable
    @Override
    public Author mapRow(ResultSet rs, int i) throws SQLException {
        return new Author(rs.getLong(F_ID), rs.getString(F_NAME));
    }
}