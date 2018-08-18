package ru.otus.homework.interfaces.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.models.BookComment;

import java.util.List;

public interface BookCommentDao extends CrudRepository<BookComment, Long> {
    List<BookComment> findAllByBookBriefId(long bookBriefId);
    void deleteByBookBriefId(long bookBriefId);
}
