package ru.otus.homework.interfaces.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.models.Author;

public interface AuthorDao extends MongoRepository<Author, String> {
}
