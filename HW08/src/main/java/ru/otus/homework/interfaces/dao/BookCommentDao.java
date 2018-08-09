package ru.otus.homework.interfaces.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.models.BookComment;

import java.util.List;

public interface BookCommentDao extends MongoRepository<BookComment, String> {
    List<BookComment> findAllByBookBriefId(String bookBriefId);
    void deleteByBookBriefId(String bookBriefId);
}
