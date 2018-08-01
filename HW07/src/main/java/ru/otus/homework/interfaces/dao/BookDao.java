package ru.otus.homework.interfaces.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.models.Book;

import java.util.List;

public interface BookDao extends CrudRepository<Book, Long> {
/*
    Book insert(Book book);
    Book update(Book book);
    Book save(Book book);
    void remove(long id);
    Optional<Book> getById(long id);
    List<Book> getAll();
    Optional<BookBrief> getBookBriefById(long id);
*/
    List<Book> findAll();
}
