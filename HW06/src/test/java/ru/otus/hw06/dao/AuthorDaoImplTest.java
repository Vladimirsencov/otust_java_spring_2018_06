package ru.otus.hw06.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.hw06.interfaces.dao.AuthorDao;
import ru.otus.hw06.models.Author;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static ru.otus.hw06.dao.DAOTestConst.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorDaoImplTest {
    private static final long FIRST_TEST_AUTHOR_ID = 1L;
    private static final long SECOND_TEST_AUTHOR_ID = 2L;

    @SpyBean
    @Autowired
    private AuthorDao authorDao;

    @Test
    public void insert() throws Exception {
        Author author = new Author(null, TEST_AUTHOR_NAME);
        Author insertedAuthor = authorDao.insert(author).orElse(null);
        assertTrue(insertedAuthor != null && insertedAuthor.getId() != null && insertedAuthor.getId() == FIRST_TEST_AUTHOR_ID);
    }

    @Test
    public void update() throws Exception {
        Author author = new Author(null, TEST_AUTHOR_NAME);
        author = authorDao.insert(author).orElse(null);
        author.setName(TEST_AUTHOR_NAME2);
        Author updatedAuthor = authorDao.update(author).orElse(null);
        assertNotNull(updatedAuthor);
        assertEquals(TEST_AUTHOR_NAME2, updatedAuthor.getName());
    }

    @Test
    public void save() throws Exception {
        Author author = new Author(null, TEST_AUTHOR_NAME);

        Author insertedAuthor = authorDao.save(author).orElse(null);
        verify(authorDao).insert(any());

        authorDao.save(insertedAuthor);
        verify(authorDao).update(any());
    }

    @Test
    public void saveListOfAuthors() throws Exception {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(null, TEST_AUTHOR_NAME));
        authors.add(new Author(null, TEST_AUTHOR_NAME2));

        List<Author> insertedAuthors = authorDao.saveList(authors);
        insertedAuthors.sort(Comparator.comparingLong(Author::getId));
        authors.get(0).setId(FIRST_TEST_AUTHOR_ID);
        authors.get(1).setId(SECOND_TEST_AUTHOR_ID);

        assertEquals(authors, insertedAuthors);
    }

    @Test
    public void remove() throws Exception {
        Author author = new Author(null, TEST_AUTHOR_NAME);
        authorDao.save(author);
        author = authorDao.getById(FIRST_TEST_AUTHOR_ID).orElse(null);
        assertNotNull(author);
        authorDao.remove(FIRST_TEST_AUTHOR_ID);
        author = authorDao.getById(FIRST_TEST_AUTHOR_ID).orElse(null);
        assertNull(author);
    }

    @Test
    public void getIdByName() throws Exception {
        long id = authorDao.getIdByName(TEST_AUTHOR_NAME);
        assertEquals(-FIRST_TEST_AUTHOR_ID, id);

        Author author = new Author(null, TEST_AUTHOR_NAME);
        authorDao.save(author);

        id = authorDao.getIdByName(TEST_AUTHOR_NAME);
        assertEquals(FIRST_TEST_AUTHOR_ID, id);
    }

    @Test
    public void getById() throws Exception {
        Author author = new Author(null, TEST_AUTHOR_NAME);
        authorDao.save(author);
        Author insertedAuthor = authorDao.getById(FIRST_TEST_AUTHOR_ID).orElse(null);
        assertNotNull(insertedAuthor);

        author.setId(FIRST_TEST_AUTHOR_ID);
        assertEquals(author, insertedAuthor);
    }

    @Test
    public void getByName() throws Exception {
        Author author = new Author(null, TEST_AUTHOR_NAME);
        authorDao.save(author);
        Author insertedAuthor = authorDao.getByName(TEST_AUTHOR_NAME).orElse(null);
        assertNotNull(insertedAuthor);

        author.setId(FIRST_TEST_AUTHOR_ID);
        assertEquals(author, insertedAuthor);
    }

}