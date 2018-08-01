package ru.otus.homework.interfaces.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.BookBrief;
import ru.otus.homework.models.BookComment;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static ru.otus.homework.interfaces.dao.DAOTestConst.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class BookCommentDaoTest {

    private static final String TEST_COMMENT = "Все очень хорошо!";
    private static final String TEST_COMMENT2 = "Все очень отвратительно!";

    @Autowired
    private BookDao bookDao;

    @Autowired
    private BookBriefDao bookBriefDao;

    @Autowired
    private BookCommentDao commentDao;

    private BookBrief testBookBrief;
    private BookComment testComment;

    @Before
    public void setUp() throws Exception {
        Book testBook = new Book(null, TEST_BOOK_NAME, TEST_BOOK_DESC, TEST_BOOK_PUB_YEAR, null, null);
        bookDao.save(testBook);
        testBookBrief = bookBriefDao.findById(1L).orElse(null);
        testComment = new BookComment(null, new Date(), TEST_AUTHOR_NAME, TEST_COMMENT, testBookBrief);
    }

    @Test
    public void save() throws Exception {
        BookComment insertedComment = commentDao.save(testComment);
        assertTrue(insertedComment != null && insertedComment.getId() != null && insertedComment.getId() == 1L);
    }

    @Test
    public void deleteById() throws Exception {
        commentDao.save(testComment);
        List<BookComment> comments = commentDao.findAllByBookBriefId(1L);
        assertTrue(comments != null && comments.size() == 1);

        commentDao.deleteById(1L);
        comments = commentDao.findAllByBookBriefId(1L);
        assertNotNull(comments);
        assertEquals(0, comments.size());
    }

    @Test
    public void getAllByBookId() throws Exception {
        BookComment testComment2 = new BookComment(null, new Date(), TEST_AUTHOR_NAME2, TEST_COMMENT2, testBookBrief);
        testComment = commentDao.save(testComment);
        testComment2 = commentDao.save(testComment2);
        List<BookComment> expectedComments = Arrays.asList(testComment, testComment2);
        List<BookComment> actualComments = commentDao.findAllByBookBriefId(1L);
        assertEquals(expectedComments, actualComments);
    }

}