package ru.otus.hw05.interfaces.services;

import ru.otus.hw05.models.Author;
import ru.otus.hw05.models.Book;
import ru.otus.hw05.models.Genre;

import java.util.List;
import java.util.Optional;

public interface DataStorageService {

    Optional<Author> insertAuthor(Author author);
    Optional<Author> updateAuthor(Author author);
    Optional<Author> saveAuthor(Author author);
    List<Author> saveAuthorsList(List<Author> authors);

    boolean removeAuthor(long id);
    long getAuthorIdByName(String name);
    Optional<Author> getAuthorById(long id);
    Optional<Author> getAuthorByName(String name);

    Optional<Genre> insertGenre(Genre genre);
    Optional<Genre> updateGenre(Genre genre);
    Optional<Genre> saveGenre(Genre genre);
    List<Genre> saveGenresList(List<Genre> genres);


    boolean removeGenre(long id);
    long getGenreIdByName(String name);
    Optional<Genre> getGenreById(long id);
    Optional<Genre> getGenreByName(String name);

    Optional<Book> insertBook(Book book);
    Optional<Book> updateBook(Book book);
    Optional<Book> saveBook(Book book);
    boolean removeBook(long id);
    Optional<Book> getBookById(long id);
    Optional<Long> getBookIdByNameAndDescription(String name, String description);
    List<Book> getAllBooks();

}
