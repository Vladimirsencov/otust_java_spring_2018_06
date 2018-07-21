package ru.otus.hw06.interfaces.dao;

import ru.otus.hw06.models.BookComment;

import java.util.List;
import java.util.Optional;

public interface BookCommentDao {
    Optional<BookComment> insert(BookComment comment);
    boolean remove(long id);
    List<BookComment> getAllByBookId(long bookId);
}
