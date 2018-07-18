package ru.otus.hw05.interfaces.services;

import ru.otus.hw05.interfaces.dao.AuthorDao;
import ru.otus.hw05.interfaces.dao.BookDao;
import ru.otus.hw05.interfaces.dao.GenreDao;
import ru.otus.hw05.models.Author;
import ru.otus.hw05.models.Book;
import ru.otus.hw05.models.Genre;

import java.util.List;

public interface DataStorageService {

    Author insertAuthor(Author author);
    Author updateAuthor(Author author);
    Author saveAuthor(Author author);
    List<Author> saveAuthorsList(List<Author> authors);

    boolean removeAuthor(long id);
    long getAuthorIdByName(String name);
    Author getAuthorById(long id);
    Author getAuthorByName(String name);

    Genre insertGenre(Genre genre);
    Genre updateGenre(Genre genre);
    Genre saveGenre(Genre genre);
    List<Genre> saveGenresList(List<Genre> genres);


    boolean removeGenre(long id);
    long getGenreIdByName(String name);
    Genre getGenreById(long id);
    Genre getGenreByName(String name);

    Book insertBook(Book book);
    Book updateBook(Book book);
    Book saveBook(Book book);
    boolean removeBook(long id);
    Book getBookById(long id);
    Long getBookIdByNameAndDescription(String name, String description);
    List<Book> getAllBooks();

}
