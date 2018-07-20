package ru.otus.hw05.interfaces.dao;

import ru.otus.hw05.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    Optional<Author> insert(Author author);
    Optional<Author> update(Author author);
    Optional<Author> save(Author author);
    List<Author> saveList(List<Author> authors);

    boolean remove(long id);
    long getIdByName(String name);
    Optional<Author> getById(long id);
    Optional<Author> getByName(String name);
}
