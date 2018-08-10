package ru.otus.homework.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.interfaces.dao.*;
import ru.otus.homework.models.Book;

@Repository
@Transactional
public class BookDaoImpl implements BookDaoCustom {


    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final BookDao bookDao;
    private final BookCommentDao commentDao;

    @Lazy
    @Autowired
    public BookDaoImpl(AuthorDao authorDao, GenreDao genreDao, BookDao bookDao, BookCommentDao commentDao) {
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.bookDao = bookDao;
        this.commentDao = commentDao;
    }

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

    @Override
    public void deleteByIdWithComments(String id) {
        commentDao.deleteByBookBriefId(id);
        bookDao.deleteById(id);
    }
}
