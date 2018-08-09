package ru.otus.homework.interfaces.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.models.BookBrief;

public interface BookBriefDao extends MongoRepository<BookBrief, String> {
}
