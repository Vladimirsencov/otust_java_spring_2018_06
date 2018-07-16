package ru.otus.hw05.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.hw05.interfaces.dao.AuthorDao;
import ru.otus.hw05.models.Author;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorDaoImplTest {
    private static final String TEMP_AUTHOR_NAME = "Vasya";
    private static final String TEMP_AUTHOR_NAME2 = "Igor";

    @Autowired
    private AuthorDao authorDao;

/*
    @Autowired
    JdbcOperations ops;

    @Before
    public void setUp() throws Exception {
        ops.update("runscript from 'classpath:init.sql'");
    }
*/

    @Test
    public void insert() throws Exception {
        Author author = new Author(null, TEMP_AUTHOR_NAME);
        Author insertedAuthor = authorDao.insert(author);
        assertNotNull(insertedAuthor);
        assertNotNull(insertedAuthor.getId());
        long id = insertedAuthor.getId();
        assertEquals(1L, id);
    }

    @Test
    public void update() throws Exception {
        Author author = new Author(null, TEMP_AUTHOR_NAME);
        author = authorDao.insert(author);
        author.setName(TEMP_AUTHOR_NAME2);
        Author updatedAuthor = authorDao.update(author);
        assertNotNull(updatedAuthor);
        assertEquals(TEMP_AUTHOR_NAME2, updatedAuthor.getName());
    }

    @Test
    public void save() throws Exception {
        Author author = new Author(null, TEMP_AUTHOR_NAME);
        Author insertedAuthor = authorDao.save(author);
        assertNotNull(insertedAuthor);
        assertNotNull(insertedAuthor.getId());
        long id = insertedAuthor.getId();
        assertEquals(1L, id);

        insertedAuthor.setName(TEMP_AUTHOR_NAME2);
        Author updatedAuthor = authorDao.save(insertedAuthor);
        assertNotNull(updatedAuthor);
        assertEquals(TEMP_AUTHOR_NAME2, updatedAuthor.getName());
    }

    @Test
    public void saveListOfAuthors() throws Exception {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(null, TEMP_AUTHOR_NAME));
        authors.add(new Author(null, TEMP_AUTHOR_NAME2));

        List<Author> insertedAuthors = authorDao.save(authors);
        insertedAuthors.sort(Comparator.comparingLong(Author::getId));
        authors.get(0).setId(1L);
        authors.get(1).setId(2L);

        assertEquals(authors, insertedAuthors);
    }

    @Test
    public void remove() throws Exception {
        Author author = new Author(null, TEMP_AUTHOR_NAME);
        authorDao.save(author);
        author = authorDao.getById(1L);
        assertNotNull(author);
        authorDao.remove(1L);
        author = authorDao.getById(1L);
        assertNull(author);
    }

    @Test
    public void getIdByName() throws Exception {
        long id = authorDao.getIdByName(TEMP_AUTHOR_NAME);
        assertEquals(- 1L, id);

        Author author = new Author(null, TEMP_AUTHOR_NAME);
        authorDao.save(author);

        id = authorDao.getIdByName(TEMP_AUTHOR_NAME);
        assertEquals(1L, id);
    }

    @Test
    public void getById() throws Exception {
        Author author = new Author(null, TEMP_AUTHOR_NAME);
        authorDao.save(author);
        Author insertedAuthor = authorDao.getById(1L);
        assertNotNull(insertedAuthor);

        author.setId(1L);
        assertEquals(author, insertedAuthor);
    }

    @Test
    public void getByName() throws Exception {
        Author author = new Author(null, TEMP_AUTHOR_NAME);
        authorDao.save(author);
        Author insertedAuthor = authorDao.getByName(TEMP_AUTHOR_NAME);
        assertNotNull(insertedAuthor);

        author.setId(1L);
        assertEquals(author, insertedAuthor);
    }

}