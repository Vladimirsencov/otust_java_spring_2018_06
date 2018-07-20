package ru.otus.hw05.interfaces.dao;

import ru.otus.hw05.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    Optional<Book> insert(Book book);
    Optional<Book> update(Book book);
    Optional<Book> save(Book book);
    boolean remove(long id);
    Optional<Book> getById(long id);
    Optional<Long> getIdByNameAndDescription(String name, String description);
    List<Book> getAll();


}
