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
    public Optional<Book> insert(Book book) {
        Map<String, Object> params = new HashMap<>(3);
        params.put("name", book.getName());
        params.put("description", book.getDescription());
        params.put("pub_year", book.getPubYear());

        ops.update("insert into books(name, description, pub_year) (select :name, :description, :pub_year where not exists(select 1 from books where name = :name and description = :description))", params);

        Long id = getIdByNameAndDescription(book.getName(), book.getDescription()).orElse(null);
        if (id == null) {
            return Optional.empty();
        }

        insertBookAuthors(id, book.getAuthors());
        insertBookGenres(id, book.getGenres());

        return getById(id);
    }

    @Override
    public Optional<Book> update(Book book) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", book.getId());
        params.put("name", book.getName());
        params.put("description", book.getDescription());
        params.put("pub_year", book.getPubYear());

        ops.update("update books set name = :name, description = :description, pub_year = :pub_year where id = :id", params);
        insertBookAuthors(book.getId(), book.getAuthors());
        insertBookGenres(book.getId(), book.getGenres());

        return getById(book.getId());
    }

    @Override
    public Optional<Book> save(Book book) {
        if (book.getId() != null && book.getId() > 0) {
            return update(book);
        } else {
            return insert(book);
        }

    }

    @Override
    public boolean remove(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return ops.update("delete from books where id = :id", params) == 1;

    }

    @Override
    public Optional<Book> getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            Book book = ops.queryForObject("select * from books where id = :id", params, new BookRowMapper());
            book.setAuthors(getBookAuthors(book.getId()));
            book.setGenres(getBookGenres(book.getId()));
            return Optional.ofNullable(book);
        } catch (EmptyResultDataAccessException ignored){
        }
        return Optional.empty();
    }

    @Override
    public Optional<Long> getIdByNameAndDescription(String name, String description) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("name", name);
        params.put("description", description);
        try {
            return Optional.ofNullable(ops.queryForObject("select id from books where name = :name and description = :description", params, Long.class));
        } catch (EmptyResultDataAccessException ignored){
        }
        return Optional.empty();
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = ops.query("select * from books order by pub_year, name", new BookRowMapper());
        Map<Long, Book> bookMap = books.stream().collect(Collectors.toMap(Book::getId, book -> book));


        ops.query("select rel.book_id, src.id, src.name from books_authors rel left join authors src on rel.author_id = src.id order by rel.book_id, src.name", rs -> {
            if (bookMap.containsKey(rs.getLong("book_id"))) {
                bookMap.get(rs.getLong("book_id")).getAuthors().add(new Author(rs.getLong("id"), rs.getString("name")));
            }
        });

        ops.query("select rel.book_id, src.id, src.name from books_genres rel left join genres src on rel.genre_id = src.id order by rel.book_id, src.name", rs -> {
            if (bookMap.containsKey(rs.getLong("book_id"))) {
                bookMap.get(rs.getLong("book_id")).getGenres().add(new Genre(rs.getLong("id"), rs.getString("name")));
            }
        });

        return books;
    }

    private void removeBookAuthors(long bookId) {
        Map<String, Object> params = Collections.singletonMap("book_id", bookId);
        ops.update("delete from books_authors where book_id = :book_id", params);

    }

    private void removeBookGenres(long bookId) {
        Map<String, Object> params = Collections.singletonMap("book_id", bookId);
        ops.update("delete from books_genres where book_id = :book_id", params);
    }

    private void insertBookAuthors(long bookId, List<Author> authors) {
        removeBookAuthors(bookId);
        if (authors == null || authors.isEmpty()) {
            return;
        }

        List<Author> savedAuthors = authorDao.saveList(authors);
        ops.getJdbcOperations().batchUpdate("insert into books_authors(book_id, author_id) values(?, ?)", savedAuthors, savedAuthors.size(), (ps, a) -> {
            ps.setLong(1, bookId);
            ps.setLong(2, a.getId());
        });
    }

    private List<Author> getBookAuthors(long bookId) {
        Map<String, Object> params = Collections.singletonMap("book_id", bookId);
        return ops.query("select src.id, src.name from books_authors rel left join authors src on rel.author_id = src.id where rel.book_id = :book_id", params, new AuthorRowMapper());
    }

    private void insertBookGenres(long bookId, List<Genre> genres) {
        removeBookGenres(bookId);
        if (genres == null || genres.isEmpty()) {
            return;
        }

        List<Genre> savedGenres = genreDao.save(genres);
        ops.getJdbcOperations().batchUpdate("insert into books_genres(book_id, genre_id) values(?, ?)", savedGenres, savedGenres.size(), (ps, g) -> {
            ps.setLong(1, bookId);
            ps.setLong(2, g.getId());
        });
    }

    private List<Genre> getBookGenres(long bookId) {
        Map<String, Object> params = Collections.singletonMap("book_id", bookId);
        return ops.query("select src.id, src.name from books_genres rel left join genres src on rel.genre_id = src.id where rel.book_id = :book_id", params, new GenreRowMapper());
    }

}
