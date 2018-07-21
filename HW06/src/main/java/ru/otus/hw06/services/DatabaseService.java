package ru.otus.hw06.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.hw06.interfaces.dao.BookCommentDao;
import ru.otus.hw06.interfaces.dao.BookDao;
import ru.otus.hw06.interfaces.services.DataStorageService;
import ru.otus.hw06.models.*;

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
    public Optional<Book> saveBook(Book book) {
        return bookDao.save(book);
    }

    @Override
    public boolean removeBook(long id) {
        return bookDao.remove(id);
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
    public Optional<BookComment> insertBookComment(BookComment comment) {
        return commentDao.insert(comment);
    }

    @Override
    public boolean removeBookComment(long id) {
        return commentDao.remove(id);
    }

    @Override
    public List<BookComment> getAllBookCommentsByBookId(long bookId) {
        return commentDao.getAllByBookId(bookId);
    }
}
