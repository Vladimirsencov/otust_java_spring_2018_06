package ru.otus.homework.interfaces.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.BookBrief;
import ru.otus.homework.models.Genre;

import java.util.*;

import static org.junit.Assert.*;
import static ru.otus.homework.DAOTestConst.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class BookDaoTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    AuthorDao authorDao;

    @Autowired
    GenreDao genreDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private BookBriefDao bookBriefDao;


    private Book testBook;

    @Before
    public void setUp() throws Exception {
        mongoTemplate.getDb().drop();

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
        assertTrue(insertedBook != null && insertedBook.getId() != null);

    }

    @Test
    public void update() throws Exception {
        testBook.getAuthors().forEach(a -> authorDao.save(a));
        testBook.getGenres().forEach(g -> genreDao.save(g));
        Book insertedBook = bookDao.save(testBook);

        insertedBook.setName(TEST_BOOK_NAME2);
        insertedBook.setDescription(TEST_BOOK_DESC2);
        insertedBook.setPubYear(TEST_BOOK_PUB_YEAR2);
        insertedBook.getAuthors().add(new Author(null, TEST_AUTHOR_NAME3));
        insertedBook.getGenres().add(new Genre(null, TEST_GENRE_NAME3));

        testBook.getAuthors().forEach(a -> authorDao.save(a));
        testBook.getGenres().forEach(g -> genreDao.save(g));
        Book updatedBook = bookDao.save(insertedBook);
        sortBookAuthorsAndGenresById(updatedBook);

        //insertedBook.getAuthors().get(2).setId(3L);
        //insertedBook.getGenres().get(2).setId(3L);
        //assertEquals(insertedBook, updatedBook);
    }

    @Test
    public void remove() throws Exception {
        Book insertedBook = bookDao.save(testBook);
        assertNotNull(insertedBook);

        bookDao.deleteById(insertedBook.getId());
        insertedBook = bookDao.findById(insertedBook.getId()).orElse(null);
        assertNull(insertedBook);
    }

    @Test
    public void getById() throws Exception {
        testBook.getAuthors().clear();
        testBook.getGenres().clear();
        testBook = bookDao.save(testBook);

        Book insertedBook = bookDao.findById(testBook.getId()).orElse(null);
        assertEquals(testBook, insertedBook);
    }

/*
    @Test
    public void getAll() throws Exception {
        Book testBook2 = new Book(null, TEST_BOOK_NAME2, TEST_BOOK_DESC2, TEST_BOOK_PUB_YEAR2,
                Arrays.asList(new Author(null, TEST_AUTHOR_NAME3)),
                Arrays.asList(new Genre(null, TEST_GENRE_NAME3))
        );

        testBook = bookDao.saveWithAuthorsAndGenresWithAuthorsAndGenres(testBook);
        testBook2 = bookDao.saveWithAuthorsAndGenresWithAuthorsAndGenres(testBook2);

        List<Book> expectedBooks = Arrays.asList(testBook, testBook2);
        List<Book> actualBooks = bookDao.findAll();

        expectedBooks.sort(Comparator.comparing(Book::getId));
        expectedBooks.forEach(this::sortBookAuthorsAndGenresById);

        actualBooks.sort(Comparator.comparing(Book::getId));
        actualBooks.forEach(this::sortBookAuthorsAndGenresById);

        assertEquals(expectedBooks, actualBooks);

    }
*/

    @Test
    public void getBookBriefById() throws Exception {
        Book insertedBook = bookDao.save(testBook);
        BookBrief testBookBrief = new BookBrief(insertedBook.getId(), testBook.getName());

        BookBrief insertedBookBrief = bookBriefDao.findById(insertedBook.getId()).orElse(null);
        assertEquals(testBookBrief, insertedBookBrief);
    }

    private void sortBookAuthorsAndGenresById(Book book) {
        book.getAuthors().sort(Comparator.comparing(Author::getId));
        book.getGenres().sort(Comparator.comparing(Genre::getId));
    }
}