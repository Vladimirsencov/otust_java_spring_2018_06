package ru.otus.homework.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.interfaces.dao.*;
import ru.otus.homework.interfaces.services.DataStorageService;
import ru.otus.homework.models.*;

import java.util.List;
import java.util.Optional;

@Service
public class DatabaseService implements DataStorageService {

    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final BookDaoCustom bookDao;
    private final BookBriefDao bookBriefDao;
    private final BookCommentDao commentDao;


    @Autowired
    public DatabaseService(AuthorDao authorDao, GenreDao genreDao, BookDao bookDao, BookBriefDao bookBriefDao, BookCommentDao commentDao) {
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.bookDao = bookDao;
        this.bookBriefDao = bookBriefDao;
        this.commentDao = commentDao;
    }


    @Override
    public Book saveBook(Book book) {
        if (book.getAuthors() != null) {
            book.getAuthors().forEach(authorDao::save);
        }

        if (book.getGenres() != null) {
            book.getGenres().forEach(g -> genreDao.save(g));
        }

        return bookDao.save(book);
    }

    @Override
    @Transactional
    public void removeBook(String id) {
        bookDao.deleteById(id);
        commentDao.deleteByBookBriefId(id);
    }

    @Override
    public Optional<Book> getBookById(String id) {
        return bookDao.findById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.findAll();
    }

    @Override
    public Optional<BookBrief> getBookBriefById(String id) {
        return bookBriefDao.findById(id);
    }

    @Override
    public BookComment insertBookComment(BookComment comment) {
        return commentDao.save(comment);
    }

    @Override
    public void removeBookComment(String id) {
        commentDao.deleteById(id);
    }

    @Override
    public List<BookComment> getAllBookCommentsByBookId(String bookId) {
        return commentDao.findAllByBookBriefId(bookId);
    }
}
