package ru.otus.homework.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.interfaces.dao.BookBriefDao;
import ru.otus.homework.interfaces.dao.BookCommentDao;
import ru.otus.homework.interfaces.dao.BookDao;
import ru.otus.homework.interfaces.services.DataStorageService;
import ru.otus.homework.models.*;

import java.util.List;
import java.util.Optional;

@Service
public class DatabaseService implements DataStorageService {

    private final BookDao bookDao;
    private final BookBriefDao bookBriefDao;
    private final BookCommentDao commentDao;


    @Autowired
    public DatabaseService(BookDao bookDao, BookBriefDao bookBriefDao, BookCommentDao commentDao) {
        this.bookDao = bookDao;
        this.bookBriefDao = bookBriefDao;
        this.commentDao = commentDao;
    }


    @Override
    public Book saveBook(Book book) {
        return bookDao.save(book);
    }

    @Override
    public void removeBook(long id) {
        bookDao.deleteById(id);
    }

    @Override
    public Optional<Book> getBookById(long id) {
        return bookDao.findById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.findAll();
    }

    @Override
    public Optional<BookBrief> getBookBriefById(long id) {
        return bookBriefDao.findById(id);
    }

    @Override
    public BookComment insertBookComment(BookComment comment) {
        return commentDao.save(comment);
    }

    @Override
    public void removeBookComment(long id) {
        commentDao.deleteById(id);
    }

    @Override
    public List<BookComment> getAllBookCommentsByBookId(long bookId) {
        return commentDao.findAllByBookBriefId(bookId);
    }
}
