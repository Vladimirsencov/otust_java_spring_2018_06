package ru.otus.hw05.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.hw05.interfaces.dao.AuthorDao;
import ru.otus.hw05.models.Author;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static ru.otus.hw05.dao.DAOTestConst.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorDaoImplTest {
    @SpyBean
    @Autowired
    private AuthorDao authorDao;

    @Test
    public void insert() throws Exception {
        Author author = new Author(null, TEST_AUTHOR_NAME);
        Author insertedAuthor = authorDao.insert(author);
        assertTrue(insertedAuthor != null && insertedAuthor.getId() != null && insertedAuthor.getId() == 1L);
    }

    @Test
    public void update() throws Exception {
        Author author = new Author(null, TEST_AUTHOR_NAME);
        author = authorDao.insert(author);
        author.setName(TEST_AUTHOR_NAME2);
        Author updatedAuthor = authorDao.update(author);
        assertNotNull(updatedAuthor);
        assertEquals(TEST_AUTHOR_NAME2, updatedAuthor.getName());
    }

    @Test
    public void save() throws Exception {
        Author author = new Author(null, TEST_AUTHOR_NAME);

        Author insertedAuthor = authorDao.save(author);
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
        authors.get(0).setId(1L);
        authors.get(1).setId(2L);

        assertEquals(authors, insertedAuthors);
    }

    @Test
    public void remove() throws Exception {
        Author author = new Author(null, TEST_AUTHOR_NAME);
        authorDao.save(author);
        author = authorDao.getById(1L);
        assertNotNull(author);
        authorDao.remove(1L);
        author = authorDao.getById(1L);
        assertNull(author);
    }

    @Test
    public void getIdByName() throws Exception {
        long id = authorDao.getIdByName(TEST_AUTHOR_NAME);
        assertEquals(- 1L, id);

        Author author = new Author(null, TEST_AUTHOR_NAME);
        authorDao.save(author);

        id = authorDao.getIdByName(TEST_AUTHOR_NAME);
        assertEquals(1L, id);
    }

    @Test
    public void getById() throws Exception {
        Author author = new Author(null, TEST_AUTHOR_NAME);
        authorDao.save(author);
        Author insertedAuthor = authorDao.getById(1L);
        assertNotNull(insertedAuthor);

        author.setId(1L);
        assertEquals(author, insertedAuthor);
    }

    @Test
    public void getByName() throws Exception {
        Author author = new Author(null, TEST_AUTHOR_NAME);
        authorDao.save(author);
        Author insertedAuthor = authorDao.getByName(TEST_AUTHOR_NAME);
        assertNotNull(insertedAuthor);

        author.setId(1L);
        assertEquals(author, insertedAuthor);
    }

}