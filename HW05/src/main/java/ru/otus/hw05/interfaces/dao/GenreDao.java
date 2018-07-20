package ru.otus.hw05.interfaces.dao;

import ru.otus.hw05.models.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    Optional<Genre> insert(Genre genre);
    Optional<Genre> update(Genre genre);
    Optional<Genre> save(Genre genre);
    List<Genre> save(List<Genre> genres);


    boolean remove(long id);
    long getIdByName(String name);
    Optional<Genre> getById(long id);
    Optional<Genre> getByName(String name);

}
