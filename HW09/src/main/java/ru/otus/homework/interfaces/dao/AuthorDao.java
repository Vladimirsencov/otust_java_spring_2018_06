package ru.otus.homework.interfaces.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.models.Author;

import java.util.Optional;

public interface AuthorDao extends CrudRepository<Author, Long> {
    Optional<Author> findByName(String name);
}
