package ru.otus.hw06.interfaces.dao;

import ru.otus.hw06.models.BookComment;

import java.util.List;

public interface BookCommentDao {
    BookComment insert(BookComment comment);
    void remove(long id);
    List<BookComment> getAllByBookId(long bookId);
}
