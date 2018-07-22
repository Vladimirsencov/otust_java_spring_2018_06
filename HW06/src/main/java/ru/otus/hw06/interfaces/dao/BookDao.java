package ru.otus.hw06.interfaces.dao;

import ru.otus.hw06.models.Book;
import ru.otus.hw06.models.BookBrief;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    Book insert(Book book);
    Book update(Book book);
    Book save(Book book);
    void remove(long id);
    Optional<Book> getById(long id);
    List<Book> getAll();
    Optional<BookBrief> getBookBriefById(long id);
}
