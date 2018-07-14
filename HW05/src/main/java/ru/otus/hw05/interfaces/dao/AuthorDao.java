package ru.otus.hw05.interfaces.dao;

import ru.otus.hw05.models.Author;

import java.util.List;

public interface AuthorDao {
    Author insert(Author author);
    Author update(Author author);
    Author save(Author author);
    List<Author> save(List<Author> authors);

    boolean remove(long id);
    long getIdByName(String name);
    Author getById(long id);
    Author getByName(String name);
}
