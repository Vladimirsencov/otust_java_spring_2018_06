package ru.otus.homework.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.otus.homework.interfaces.dao.AuthorDao;
import ru.otus.homework.interfaces.dao.BookDao;
import ru.otus.homework.interfaces.dao.BookDaoCustom;
import ru.otus.homework.interfaces.dao.GenreDao;
import ru.otus.homework.models.Book;

@Repository
public class BookDaoImpl implements BookDaoCustom {

    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final BookDao bookDao;

    @Autowired
    public BookDaoImpl(AuthorDao authorDao, GenreDao genreDao, BookDao bookDao) {
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.bookDao = bookDao;
    }

    @Override
    public Book save(Book book) {
        if (book.getAuthors() != null) {
            book.getAuthors().forEach(authorDao::save);
        }

        if (book.getGenres() != null) {
            book.getGenres().forEach(genreDao::save);
        }

        return null;
        //return bookDao.save(book);
    }
}
