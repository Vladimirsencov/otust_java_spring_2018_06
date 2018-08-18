package ru.otus.homework.interfaces.services;

import ru.otus.homework.models.*;

import java.util.List;
import java.util.Optional;

public interface DataStorageService {

    Book saveBook(Book book);
    void removeBook(long id);
    Optional<Book> getBookById(long id);
    List<Book> getAllBooks();
    Optional<BookBrief> getBookBriefById(long id);

    BookComment saveBookComment(BookComment comment);
    void removeBookComment(long id);
    List<BookComment> getAllBookCommentsByBookId(long bookId);
    Optional<BookComment> getBookCommentById(long id);
}
