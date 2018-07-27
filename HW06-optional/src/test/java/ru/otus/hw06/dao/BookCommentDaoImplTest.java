package ru.otus.hw06.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.hw06.interfaces.dao.BookCommentDao;
import ru.otus.hw06.interfaces.dao.BookDao;
import ru.otus.hw06.models.Book;
import ru.otus.hw06.models.BookBrief;
import ru.otus.hw06.models.BookComment;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static ru.otus.hw06.dao.DAOTestConst.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class BookCommentDaoImplTest {

    private static final String TEST_COMMENT = "Все очень хорошо!";
    private static final String TEST_COMMENT2 = "Все очень отвратительно!";

    @Autowired
    private BookDao bookDao;

    @Autowired
    private BookCommentDao commentDao;

    private BookBrief testBookBrief;
    private BookComment testComment;

    @Before
    public void setUp() throws Exception {
        Book testBook = new Book(null, TEST_BOOK_NAME, TEST_BOOK_DESC, TEST_BOOK_PUB_YEAR, null, null);
        bookDao.save(testBook);
        testBookBrief = bookDao.getBookBriefById(1L).orElse(null);
        testComment = new BookComment(null, new Date(), TEST_AUTHOR_NAME, TEST_COMMENT, testBookBrief);
    }

    @Test
    public void insert() throws Exception {
        BookComment insertedComment = commentDao.insert(testComment);
        assertTrue(insertedComment != null && insertedComment.getId() != null && insertedComment.getId() == 1L);
    }

    @Test
    public void remove() throws Exception {
        commentDao.insert(testComment);
        List<BookComment> comments = commentDao.getAllByBookId(1L);
        assertTrue(comments != null && comments.size() == 1);

        commentDao.remove(1L);
        comments = commentDao.getAllByBookId(1L);
        assertNotNull(comments);
        assertEquals(0, comments.size());
    }

    @Test
    public void getAllByBookId() throws Exception {
        BookComment testComment2 = new BookComment(null, new Date(), TEST_AUTHOR_NAME2, TEST_COMMENT2, testBookBrief);
        testComment = commentDao.insert(testComment);
        testComment2 = commentDao.insert(testComment2);
        List<BookComment> expectedComments = Arrays.asList(testComment, testComment2);
        List<BookComment> actualComments = commentDao.getAllByBookId(1L);
        assertEquals(expectedComments, actualComments);
    }

}