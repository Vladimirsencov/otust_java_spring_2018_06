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

import static org.assertj.core.api.Assertions.*;
import static ru.otus.homework.TestConst.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class BookCommentDaoTest {

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
        testBook = bookDao.save(testBook);
        testBookBrief = bookBriefDao.findById(testBook.getId()).orElse(null);
        testComment = new BookComment(null, new Date(), TEST_AUTHOR_NAME, TEST_COMMENT, testBookBrief);
    }

    @Test
    public void save() throws Exception {
        BookComment insertedComment = commentDao.save(testComment);
        assertThat(insertedComment != null && insertedComment.getId() != null).isTrue();
        testComment.setId(insertedComment.getId());
        assertThat(insertedComment).isEqualTo(testComment);

        testComment.setCommentingTime(new Date());
        testComment.setAuthor(TEST_AUTHOR_NAME2);
        testComment.setComment(TEST_COMMENT2);
        insertedComment = commentDao.save(testComment);
        assertThat(insertedComment).isEqualTo(testComment);
    }

    @Test
    public void deleteById() throws Exception {
        testComment = commentDao.save(testComment);
        List<BookComment> comments = commentDao.findAllByBookBriefId(testBookBrief.getId());
        assertThat(comments != null && comments.size() == 1).isTrue();

        commentDao.deleteById(testComment.getId());
        comments = commentDao.findAllByBookBriefId(testBookBrief.getId());
        assertThat(comments).isNotNull();
        assertThat(comments.size()).isEqualTo(0);
    }

    @Test
    public void getAllByBookId() throws Exception {
        BookComment testComment2 = new BookComment(null, new Date(), TEST_AUTHOR_NAME2, TEST_COMMENT2, testBookBrief);
        testComment = commentDao.save(testComment);
        testComment2 = commentDao.save(testComment2);
        List<BookComment> expectedComments = Arrays.asList(testComment, testComment2);
        List<BookComment> actualComments = commentDao.findAllByBookBriefId(testBookBrief.getId());
        assertThat(actualComments).isEqualTo(expectedComments);
    }
}