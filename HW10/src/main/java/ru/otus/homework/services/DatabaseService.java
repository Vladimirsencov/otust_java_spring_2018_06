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
    private final BookDao bookDao;
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
        book.getAuthors().forEach(author -> {
            if (author.getId() == null) {
                authorDao.findByName(author.getName()).ifPresent(a -> author.setId(a.getId()));
            }
        });
        book.getGenres().forEach(genre -> {
            if (genre.getId() == null) {
                authorDao.findByName(genre.getName()).ifPresent(a -> genre.setId(a.getId()));
            }
        });
        return bookDao.save(book);
    }

    @Override
    @Transactional
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
    public BookComment saveBookComment(BookComment comment) {
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

    @Override
    public Optional<BookComment> getBookCommentById(long id) {
        return commentDao.findById(id);
    }
}
