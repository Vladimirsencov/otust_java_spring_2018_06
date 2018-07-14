package ru.otus.hw05.interfaces.dao;

import ru.otus.hw05.models.Book;

import java.util.List;

public interface BookDao {
    Book insert(Book book);
    Book update(Book book);
    Book save(Book book);
    boolean remove(long id);
    Book getById(long id);
    Long getIdByNameAndDescription(String name, String description);
    List<Book> getAll();


}
