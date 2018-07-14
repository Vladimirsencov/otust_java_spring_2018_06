package ru.otus.hw05.interfaces.dao;

import ru.otus.hw05.models.Genre;

import java.util.List;

public interface GenreDao {
    Genre insert(Genre genre);
    Genre update(Genre genre);
    Genre save(Genre genre);
    List<Genre> save(List<Genre> genres);


    boolean remove(long id);
    long getIdByName(String name);
    Genre getById(long id);
    Genre getByName(String name);

}
