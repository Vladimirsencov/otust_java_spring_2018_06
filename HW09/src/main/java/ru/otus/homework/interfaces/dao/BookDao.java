package ru.otus.homework.interfaces.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.models.Book;

import java.util.List;

public interface BookDao extends CrudRepository<Book, Long> {
    List<Book> findAll();
}
