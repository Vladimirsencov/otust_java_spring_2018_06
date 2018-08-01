package ru.otus.homework.interfaces.dao;

import ru.otus.homework.models.BookComment;

import java.util.List;

public interface BookCommentDao {
    BookComment insert(BookComment comment);
    void remove(long id);
    List<BookComment> getAllByBookId(long bookId);
}
