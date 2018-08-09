package ru.otus.homework.interfaces.dao;

import ru.otus.homework.models.Book;

public interface BookDaoCustom {
    Book saveWithAuthorsAndGenres(Book book);
}
