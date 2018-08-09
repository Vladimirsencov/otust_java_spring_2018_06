package ru.otus.homework.interfaces.services;

import ru.otus.homework.models.*;

import java.util.List;
import java.util.Optional;

public interface DataStorageService {

    Book saveBook(Book book);
    void removeBook(String id);
    Optional<Book> getBookById(String id);
    List<Book> getAllBooks();
    Optional<BookBrief> getBookBriefById(String id);

    BookComment insertBookComment(BookComment comment);
    void removeBookComment(String id);
    List<BookComment> getAllBookCommentsByBookId(String bookId);
}
