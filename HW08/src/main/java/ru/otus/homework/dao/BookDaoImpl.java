package ru.otus.homework.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import ru.otus.homework.interfaces.dao.AuthorDao;
import ru.otus.homework.interfaces.dao.BookDao;
import ru.otus.homework.interfaces.dao.BookDaoCustom;
import ru.otus.homework.interfaces.dao.GenreDao;
import ru.otus.homework.models.Book;

@Repository
public class BookDaoImpl implements BookDaoCustom {

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private GenreDao genreDao;

    @Lazy
    @Autowired
    private BookDao bookDao;

    

    @Override
    public Book saveWithAuthorsAndGenres(Book book) {
        if (book.getAuthors() != null) {
            book.getAuthors().forEach(authorDao::save);
        }

        if (book.getGenres() != null) {
            book.getGenres().forEach(genreDao::save);
        }

        return bookDao.save(book);
    }
}
