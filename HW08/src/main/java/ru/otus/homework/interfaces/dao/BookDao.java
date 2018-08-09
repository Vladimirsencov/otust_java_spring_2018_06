package ru.otus.homework.interfaces.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.models.Book;

import java.util.List;

public interface BookDao extends MongoRepository<Book, String>, BookDaoCustom {
    List<Book> findAll();
}
