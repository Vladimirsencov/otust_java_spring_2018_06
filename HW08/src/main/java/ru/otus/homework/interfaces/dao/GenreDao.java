package ru.otus.homework.interfaces.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.models.Genre;

public interface GenreDao extends MongoRepository<Genre, String> {
}
