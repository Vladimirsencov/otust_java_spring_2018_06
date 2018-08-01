package ru.otus.homework.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.interfaces.dao.BookCommentDao;
import ru.otus.homework.interfaces.dao.BookDao;
import ru.otus.homework.interfaces.services.DataStorageService;
import ru.otus.homework.models.*;

import java.util.List;
import java.util.Optional;

@Service
public class DatabaseService implements DataStorageService {

    private final BookDao bookDao;
    private final BookCommentDao commentDao;

    @Autowired
    public DatabaseService(BookDao bookDao, BookCommentDao commentDao) {
        this.bookDao = bookDao;
        this.commentDao = commentDao;
    }


    @Override
    public Book saveBook(Book book) {
        return bookDao.save(book);
    }

    @Override
    public void removeBook(long id) {
        bookDao.remove(id);
    }

    @Override
    public Optional<Book> getBookById(long id) {
        return bookDao.getById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAll();
    }

    @Override
    public Optional<BookBrief> getBookBriefById(long id) {
        return bookDao.getBookBriefById(id);
    }

    @Override
    public BookComment insertBookComment(BookComment comment) {
        return commentDao.insert(comment);
    }

    @Override
    public void removeBookComment(long id) {
        commentDao.remove(id);
    }

    @Override
    public List<BookComment> getAllBookCommentsByBookId(long bookId) {
        return commentDao.getAllByBookId(bookId);
    }
}
