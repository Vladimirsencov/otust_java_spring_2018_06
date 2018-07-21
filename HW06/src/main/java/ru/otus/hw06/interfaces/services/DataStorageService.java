package ru.otus.hw06.interfaces.services;

import ru.otus.hw06.models.*;

import java.util.List;
import java.util.Optional;

public interface DataStorageService {

    Optional<Book> saveBook(Book book);
    boolean removeBook(long id);
    Optional<Book> getBookById(long id);
    List<Book> getAllBooks();
    Optional<BookBrief> getBookBriefById(long id);

    Optional<BookComment> insertBookComment(BookComment comment);
    boolean removeBookComment(long id);
    List<BookComment> getAllBookCommentsByBookId(long bookId);
}
