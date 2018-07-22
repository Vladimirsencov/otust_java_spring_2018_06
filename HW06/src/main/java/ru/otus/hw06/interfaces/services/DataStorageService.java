package ru.otus.hw06.interfaces.services;

import ru.otus.hw06.models.*;

import java.util.List;
import java.util.Optional;

public interface DataStorageService {

    Book saveBook(Book book);
    void removeBook(long id);
    Optional<Book> getBookById(long id);
    List<Book> getAllBooks();
    Optional<BookBrief> getBookBriefById(long id);

    BookComment insertBookComment(BookComment comment);
    void removeBookComment(long id);
    List<BookComment> getAllBookCommentsByBookId(long bookId);
}
