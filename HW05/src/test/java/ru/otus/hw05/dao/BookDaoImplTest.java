package ru.otus.hw05.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.hw05.interfaces.dao.BookDao;
import ru.otus.hw05.models.Author;
import ru.otus.hw05.models.Book;
import ru.otus.hw05.models.Genre;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static ru.otus.hw05.dao.DAOTestConst.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookDaoImplTest {

    private static final String TEST_BOOK_NAME = "TestBook";
    private static final String TEST_BOOK_NAME2 = "TestBook2";
    private static final String TEST_BOOK_DESC = "AnyDesc";
    private static final String TEST_BOOK_DESC2 = "AnyDesc2";
    private static final int TEST_BOOK_PUB_YEAR = 2005;
    private static final int TEST_BOOK_PUB_YEAR2 = 2011;

    @SpyBean
    @Autowired
    private BookDao bookDao;

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
        Book insertedBook = bookDao.insert(testBook).orElse(null);
        assertTrue(insertedBook != null && insertedBook.getId() != null && insertedBook.getId() == 1L);
    }

    @Test
    public void update() throws Exception {
        Book insertedBook = bookDao.insert(testBook).orElse(null);

        insertedBook.setName(TEST_BOOK_NAME2);
        insertedBook.setDescription(TEST_BOOK_DESC2);
        insertedBook.setPubYear(TEST_BOOK_PUB_YEAR2);
        insertedBook.getAuthors().add(new Author(null, TEST_AUTHOR_NAME3));
        insertedBook.getGenres().add(new Genre(null, TEST_GENRE_NAME3));

        Book updatedBook = bookDao.update(insertedBook).orElse(null);
        sortBookAuthorsAndGenresById(updatedBook);

        insertedBook.getAuthors().get(2).setId(3L);
        insertedBook.getGenres().get(2).setId(3L);
        assertEquals(insertedBook, updatedBook);
    }

    @Test
    public void save() throws Exception {
        Book insertedBook = bookDao.save(testBook).orElse(null);
        verify(bookDao).insert(any());

        bookDao.save(insertedBook);
        verify(bookDao).update(any());
    }

    @Test
    public void remove() throws Exception {
        Book insertedBook = bookDao.save(testBook).orElse(null);
        assertNotNull(insertedBook);

        bookDao.remove(1L);
        insertedBook = bookDao.getById(1L).orElse(null);
        assertNull(insertedBook);
    }

    @Test
    public void getById() throws Exception {
        testBook.getAuthors().clear();
        testBook.getGenres().clear();
        bookDao.save(testBook);
        testBook.setId(1L);

        Book insertedBook = bookDao.getById(1L).orElse(null);
        assertEquals(testBook, insertedBook);
    }

    @Test
    public void getIdByNameAndDescription() throws Exception {
        bookDao.save(testBook);
        long id = bookDao.getIdByNameAndDescription(testBook.getName(), testBook.getDescription()).orElse(0L);
        assertEquals(1L, id);
    }

    @Test
    public void getAll() throws Exception {
        Book testBook2 = new Book(null, TEST_BOOK_NAME2, TEST_BOOK_DESC2, TEST_BOOK_PUB_YEAR2,
                Collections.singletonList(new Author(null, TEST_AUTHOR_NAME3)),
                Collections.singletonList(new Genre(null, TEST_GENRE_NAME3)));

        testBook = bookDao.save(testBook).orElse(null);
        testBook2 = bookDao.save(testBook2).orElse(null);

        List<Book> expectedBooks = Arrays.asList(testBook, testBook2);
        List<Book> actualBooks = bookDao.getAll();

        expectedBooks.sort(Comparator.comparingLong(Book::getId));
        expectedBooks.forEach(this::sortBookAuthorsAndGenresById);

        actualBooks.sort(Comparator.comparingLong(Book::getId));
        actualBooks.forEach(this::sortBookAuthorsAndGenresById);

        assertEquals(expectedBooks, actualBooks);

    }

    private void sortBookAuthorsAndGenresById(Book book) {
        book.getAuthors().sort(Comparator.comparingLong(Author::getId));
        book.getGenres().sort(Comparator.comparingLong(Genre::getId));
    }

}