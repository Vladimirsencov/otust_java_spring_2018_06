package ru.otus.hw05.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.hw05.interfaces.dao.AuthorDao;
import ru.otus.hw05.interfaces.dao.BookDao;
import ru.otus.hw05.interfaces.dao.GenreDao;
import ru.otus.hw05.interfaces.services.DataStorageService;
import ru.otus.hw05.models.Author;
import ru.otus.hw05.models.Book;
import ru.otus.hw05.models.Genre;

import java.util.List;

@Service
public class DatabaseService implements DataStorageService {

    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final BookDao bookDao;

    @Autowired
    public DatabaseService(AuthorDao authorDao, GenreDao genreDao, BookDao bookDao) {
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.bookDao = bookDao;
    }


    @Override
    public Author insertAuthor(Author author) {
        return authorDao.insert(author);
    }

    @Override
    public Author updateAuthor(Author author) {
        return authorDao.update(author);
    }

    @Override
    public Author saveAuthor(Author author) {
        return authorDao.save(author);
    }

    @Override
    public List<Author> saveAuthorsList(List<Author> authors) {
        return authorDao.saveList(authors);
    }

    @Override
    public boolean removeAuthor(long id) {
        return authorDao.remove(id);
    }

    @Override
    public long getAuthorIdByName(String name) {
        return authorDao.getIdByName(name);
    }

    @Override
    public Author getAuthorById(long id) {
        return authorDao.getById(id);
    }

    @Override
    public Author getAuthorByName(String name) {
        return authorDao.getByName(name);
    }

    @Override
    public Book insertBook(Book book) {
        return bookDao.insert(book);
    }

    @Override
    public Book updateBook(Book book) {
        return bookDao.update(book);
    }

    @Override
    public Book saveBook(Book book) {
        return bookDao.save(book);
    }

    @Override
    public boolean removeBook(long id) {
        return bookDao.remove(id);
    }

    @Override
    public Book getBookById(long id) {
        return bookDao.getById(id);
    }

    @Override
    public Long getBookIdByNameAndDescription(String name, String description) {
        return bookDao.getIdByNameAndDescription(name, description);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAll();
    }

    @Override
    public Genre insertGenre(Genre genre) {
        return genreDao.insert(genre);
    }

    @Override
    public Genre updateGenre(Genre genre) {
        return genreDao.update(genre);
    }

    @Override
    public Genre saveGenre(Genre genre) {
        return genreDao.save(genre);
    }

    @Override
    public List<Genre> saveGenresList(List<Genre> genres) {
        return genreDao.save(genres);
    }

    @Override
    public boolean removeGenre(long id) {
        return genreDao.remove(id);
    }

    @Override
    public long getGenreIdByName(String name) {
        return genreDao.getIdByName(name);
    }

    @Override
    public Genre getGenreById(long id) {
        return genreDao.getById(id);
    }

    @Override
    public Genre getGenreByName(String name) {
        return genreDao.getByName(name);
    }
}
