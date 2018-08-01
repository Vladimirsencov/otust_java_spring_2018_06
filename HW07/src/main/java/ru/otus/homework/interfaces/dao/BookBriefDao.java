package ru.otus.homework.interfaces.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.models.BookBrief;

public interface BookBriefDao extends CrudRepository<BookBrief, Long> {
}
