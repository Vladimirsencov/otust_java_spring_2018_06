package ru.otus.homework.interfaces.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.BookBrief;
import ru.otus.homework.models.Genre;

import java.util.*;

import static org.junit.Assert.*;
import static ru.otus.homework.interfaces.dao.DAOTestConst.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class BookDaoTest {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private BookBriefDao bookBriefDao;


    private Book testBook;

    @Before
    public void setUp() throws Exception {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(null, TEST_AUTHOR_NAME));
        authors.add(new Author(null, TEST_AUTHOR_NAME2));

        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(null, TEST_GENRE_NAME));
        genres.add(new Genre(null, TEST_GENRE_NAME2));
        testBook = new Book(null, TEST_BOOK_NAME, TEST_BOOK_DESC, TEST_BOOK_PUB_YEAR, authors, genres);
    }

    @Test
    public void insert() throws Exception {
        Book insertedBook = bookDao.save(testBook);
        assertTrue(insertedBook != null && insertedBook.getId() != null && insertedBook.getId() == 1L);

    }

    @Test
    public void update() throws Exception {
        Book insertedBook = bookDao.save(testBook);

        insertedBook.setName(TEST_BOOK_NAME2);
        insertedBook.setDescription(TEST_BOOK_DESC2);
        insertedBook.setPubYear(TEST_BOOK_PUB_YEAR2);
        insertedBook.getAuthors().add(new Author(null, TEST_AUTHOR_NAME3));
        insertedBook.getGenres().add(new Genre(null, TEST_GENRE_NAME3));

        Book updatedBook = bookDao.save(insertedBook);
        sortBookAuthorsAndGenresById(updatedBook);

        insertedBook.getAuthors().get(2).setId(3L);
        insertedBook.getGenres().get(2).setId(3L);
        assertEquals(insertedBook, updatedBook);
    }

    @Test
    public void remove() throws Exception {
        Book insertedBook = bookDao.save(testBook);
        assertNotNull(insertedBook);

        bookDao.deleteById(1L);
        insertedBook = bookDao.findById(1L).orElse(null);
        assertNull(insertedBook);
    }

    @Test
    public void getById() throws Exception {
        testBook.getAuthors().clear();
        testBook.getGenres().clear();
        bookDao.save(testBook);
        testBook.setId(1L);

        Book insertedBook = bookDao.findById(1L).orElse(null);
        assertEquals(testBook, insertedBook);
    }

    @Test
    public void getAll() throws Exception {
        Book testBook2 = new Book(null, TEST_BOOK_NAME2, TEST_BOOK_DESC2, TEST_BOOK_PUB_YEAR2,
                Arrays.asList(new Author(null, TEST_AUTHOR_NAME3)),
                Arrays.asList(new Genre(null, TEST_GENRE_NAME3))
        );

        testBook = bookDao.save(testBook);
        testBook2 = bookDao.save(testBook2);

        List<Book> expectedBooks = Arrays.asList(testBook, testBook2);
        List<Book> actualBooks = bookDao.findAll();

        expectedBooks.sort(Comparator.comparingLong(Book::getId));
        expectedBooks.forEach(this::sortBookAuthorsAndGenresById);

        actualBooks.sort(Comparator.comparingLong(Book::getId));
        actualBooks.forEach(this::sortBookAuthorsAndGenresById);

        assertEquals(expectedBooks, actualBooks);

    }

    @Test
    public void getBookBriefById() throws Exception {
        bookDao.save(testBook);
        BookBrief testBookBrief = new BookBrief(1L, testBook.getName());

        BookBrief insertedBookBrief = bookBriefDao.findById(1L).orElse(null);
        assertEquals(testBookBrief, insertedBookBrief);
    }

    private void sortBookAuthorsAndGenresById(Book book) {
        book.getAuthors().sort(Comparator.comparingLong(Author::getId));
        book.getGenres().sort(Comparator.comparingLong(Genre::getId));
    }


}