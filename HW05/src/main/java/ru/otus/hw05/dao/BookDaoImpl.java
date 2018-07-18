package ru.otus.hw05.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hw05.dao.mappers.AuthorRowMapper;
import ru.otus.hw05.dao.mappers.BookRowMapper;
import ru.otus.hw05.dao.mappers.GenreRowMapper;
import ru.otus.hw05.interfaces.dao.AuthorDao;
import ru.otus.hw05.interfaces.dao.BookDao;
import ru.otus.hw05.interfaces.dao.GenreDao;
import ru.otus.hw05.models.Author;
import ru.otus.hw05.models.Book;
import ru.otus.hw05.models.Genre;

import java.util.*;
import java.util.stream.Collectors;

import static ru.otus.hw05.dao.DBConsts.*;

@Repository
public class BookDaoImpl implements BookDao {

    private final NamedParameterJdbcOperations ops;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Autowired
    public BookDaoImpl(NamedParameterJdbcOperations ops, AuthorDao authorDao, GenreDao genreDao) {
        this.ops = ops;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public Book insert(Book book) {
        Map<String, Object> params = book2ParamsMap(book, false);

        String sql = "insert into %1$s(%2$s, %3$s, %4$s) (select :%2$s, :%3$s, :%4$s where not exists(select 1 from %1$s where %2$s = :%2$s and %3$s = :%3$s))";
        ops.update(String.format(sql, TBL_BOOKS, F_NAME, F_DESCRIPTION, F_PUB_YEAR), params);

        Long id = getIdByNameAndDescription(book.getName(), book.getDescription());
        if (id == null) {
            return null;
        }

        insertBookAuthors(id, book.getAuthors());
        insertBookGenres(id, book.getGenres());

        return getById(id);
    }

    @Override
    public Book update(Book book) {
        Map<String, Object> params = book2ParamsMap(book, true);

        ops.update(String.format("update %1$s set %2$s = :%2$s, %3$s = :%3$s, %4$s = :%4$s where %5$s = :%5$s", TBL_BOOKS, F_NAME, F_DESCRIPTION, F_PUB_YEAR, F_ID), params);
        insertBookAuthors(book.getId(), book.getAuthors());
        insertBookGenres(book.getId(), book.getGenres());

        return getById(book.getId());
    }

    @Override
    public Book save(Book book) {
        if (book.getId() != null && book.getId() > 0) {
            return update(book);
        } else {
            return insert(book);
        }

    }

    @Override
    public boolean remove(long id) {
        Map<String, Object> params = Collections.singletonMap(F_ID, id);
        return ops.update(String.format(TEMPLATE_REMOVE_BY_ID_SQL, TBL_BOOKS, F_ID, F_ID), params) == 1;

    }

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Collections.singletonMap(F_ID, id);
        try {
            Book book = ops.queryForObject(String.format(TEMPLATE_SELECT_WITH_ONE_CONDITION_SQL, "*", TBL_BOOKS, F_ID, F_ID), params, new BookRowMapper());
            book.setAuthors(getBookAuthors(book.getId()));
            book.setGenres(getBookGenres(book.getId()));
            return book;
        } catch (EmptyResultDataAccessException ignored){
        }
        return null;
    }

    @Override
    public Long getIdByNameAndDescription(String name, String description) {
        Map<String, Object> params = new HashMap<>(1);
        params.put(F_NAME, name);
        params.put(F_DESCRIPTION, description);
        try {
            return ops.queryForObject(String.format("select %1$s from %2$s where %3$s = :%3$s and %4$s = %4$s", F_ID, TBL_BOOKS, F_NAME, F_DESCRIPTION), params, Long.class);
        } catch (EmptyResultDataAccessException ignored){
        }
        return null;
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = ops.query(String.format("select * from %s order by %s, %s", TBL_BOOKS, F_PUB_YEAR, F_NAME), new BookRowMapper());
        Map<Long, Book> bookMap = books.stream().collect(Collectors.toMap(Book::getId, book -> book));

        ops.query(String.format(TEMPLATE_SELECT_ALL_FROM_RELATIONS_TABLES_SQL, F_BOOK_ID, F_ID, F_NAME, TBL_BOOKS_AUTHORS, TBL_AUTHORS, F_AUTHOR_ID), rs -> {
            if (bookMap.containsKey(rs.getLong(F_BOOK_ID))) {
                bookMap.get(rs.getLong(F_BOOK_ID)).getAuthors().add(new Author(rs.getLong(F_ID), rs.getString(F_NAME)));
            }
        });

        ops.query(String.format(TEMPLATE_SELECT_ALL_FROM_RELATIONS_TABLES_SQL, F_BOOK_ID, F_ID, F_NAME, TBL_BOOKS_GENRES, TBL_GENRES, F_GENRE_ID), rs -> {
            if (bookMap.containsKey(rs.getLong(F_BOOK_ID))) {
                bookMap.get(rs.getLong(F_BOOK_ID)).getGenres().add(new Genre(rs.getLong(F_ID), rs.getString(F_NAME)));
            }
        });

        return books;
    }

    private Map<String, Object> book2ParamsMap(Book book, boolean addId) {
        Map<String, Object> params = new HashMap<>(1);
        if (addId) {
            params.put(F_ID, book.getId());
        }
        params.put(F_NAME, book.getName());
        params.put(F_DESCRIPTION, book.getDescription());
        params.put(F_PUB_YEAR, book.getPubYear());
        return params;
    }

    private void removeBookAuthors(long bookId) {
        Map<String, Object> params = Collections.singletonMap(F_BOOK_ID, bookId);
        ops.update(String.format(TEMPLATE_REMOVE_BY_ID_SQL, TBL_BOOKS_AUTHORS, F_BOOK_ID, F_BOOK_ID), params);

    }

    private void removeBookGenres(long bookId) {
        Map<String, Object> params = Collections.singletonMap(F_BOOK_ID, bookId);
        ops.update(String.format(TEMPLATE_REMOVE_BY_ID_SQL, TBL_BOOKS_GENRES, F_BOOK_ID, F_BOOK_ID), params);
    }

    private void insertBookAuthors(long bookId, List<Author> authors) {
        removeBookAuthors(bookId);
        if (authors == null || authors.isEmpty()) {
            return;
        }

        List<Author> savedAuthors = authorDao.saveList(authors);
        ops.getJdbcOperations().batchUpdate(String.format(TEMPLATE_INSERT_INTO_RELATIONS_TABLES_SQL, TBL_BOOKS_AUTHORS, F_BOOK_ID, F_AUTHOR_ID), savedAuthors, savedAuthors.size(), (ps, a) -> {
            ps.setLong(1, bookId);
            ps.setLong(2, a.getId());
        });
    }

    private List<Author> getBookAuthors(long bookId) {
        Map<String, Object> params = Collections.singletonMap(F_BOOK_ID, bookId);
        String sql = String.format(TEMPLATE_SELECT_FROM_RELATIONS_TABLES_SQL, F_ID, F_NAME, TBL_BOOKS_AUTHORS, TBL_AUTHORS, F_AUTHOR_ID, F_BOOK_ID);
        return ops.query(sql, params, new AuthorRowMapper());
    }

    private void insertBookGenres(long bookId, List<Genre> genres) {
        removeBookGenres(bookId);
        if (genres == null || genres.isEmpty()) {
            return;
        }

        List<Genre> savedGenres = genreDao.save(genres);
        ops.getJdbcOperations().batchUpdate(String.format(TEMPLATE_INSERT_INTO_RELATIONS_TABLES_SQL, TBL_BOOKS_GENRES, F_BOOK_ID, F_GENRE_ID), savedGenres, savedGenres.size(), (ps, g) -> {
            ps.setLong(1, bookId);
            ps.setLong(2, g.getId());
        });
    }

    private List<Genre> getBookGenres(long bookId) {
        Map<String, Object> params = Collections.singletonMap(F_BOOK_ID, bookId);
        String sql = String.format(TEMPLATE_SELECT_FROM_RELATIONS_TABLES_SQL, F_ID, F_NAME, TBL_BOOKS_GENRES, TBL_GENRES, F_GENRE_ID, F_BOOK_ID);
        return ops.query(sql, params, new GenreRowMapper());
    }

}
