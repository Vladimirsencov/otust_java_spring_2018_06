package ru.otus.hw05.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import ru.otus.hw05.models.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ru.otus.hw05.dao.DBConsts.*;

public class BookRowMapper implements RowMapper<Book> {
    @Nullable
    @Override
    public Book mapRow(ResultSet rs, int i) throws SQLException {
        return new Book(rs.getLong(F_ID), rs.getString(F_NAME), rs.getString(F_DESCRIPTION), rs.getInt(F_PUB_YEAR), null, null);
    }
}
