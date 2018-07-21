package ru.otus.hw06.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.hw06.interfaces.dao.GenreDao;
import ru.otus.hw06.models.Genre;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static ru.otus.hw06.dao.DAOTestConst.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest
public class GenreDaoImplTest {

    @SpyBean
    @Autowired
    private GenreDao genreDao;

    @Test
    public void insert() throws Exception {
        Genre Genre = new Genre(null, TEST_GENRE_NAME);
        Genre insertedGenre = genreDao.insert(Genre).orElse(null);
        assertTrue(insertedGenre != null && insertedGenre.getId() != null && insertedGenre.getId() == 1L);
    }

    @Test
    public void update() throws Exception {
        Genre Genre = new Genre(null, TEST_GENRE_NAME);
        Genre = genreDao.insert(Genre).orElse(null);
        Genre.setName(TEST_GENRE_NAME2);
        Genre updatedGenre = genreDao.update(Genre).orElse(null);
        assertNotNull(updatedGenre);
        assertEquals(TEST_GENRE_NAME2, updatedGenre.getName());
    }

    @Test
    public void save() throws Exception {
        Genre genre = new Genre(null, TEST_GENRE_NAME);
        Genre insertedGenre = genreDao.save(genre).orElse(null);
        verify(genreDao).insert(any());

        genreDao.save(insertedGenre);
        verify(genreDao).update(any());
    }

    @Test
    public void saveListOfGenres() throws Exception {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(null, TEST_GENRE_NAME));
        genres.add(new Genre(null, TEST_GENRE_NAME2));

        List<Genre> insertedGenres = genreDao.save(genres);
        genres.get(0).setId(1L);
        genres.get(1).setId(2L);

        assertEquals(genres, insertedGenres);
    }

    @Test
    public void remove() throws Exception {
        Genre genre = new Genre(null, TEST_GENRE_NAME);
        genreDao.save(genre);
        genre = genreDao.getById(1L).orElse(null);
        assertNotNull(genre);
        genreDao.remove(1L);
        genre = genreDao.getById(1L).orElse(null);
        assertNull(genre);
    }

    @Test
    public void getIdByName() throws Exception {
        long id = genreDao.getIdByName(TEST_GENRE_NAME);
        assertEquals(- 1L, id);

        Genre genre = new Genre(null, TEST_GENRE_NAME);
        genre = genreDao.save(genre).orElse(null);
        System.out.println(genre);

        id = genreDao.getIdByName(TEST_GENRE_NAME);
        assertEquals(1L, id);
    }

    @Test
    public void getById() throws Exception {
        Genre genre = new Genre(null, TEST_GENRE_NAME);
        genreDao.save(genre);
        Genre insertedGenre = genreDao.getById(1L).orElse(null);
        assertNotNull(insertedGenre);

        genre.setId(1L);
        assertEquals(genre, insertedGenre);
    }

    @Test
    public void getByName() throws Exception {
        Genre genre = new Genre(null, TEST_GENRE_NAME);
        genreDao.save(genre);
        Genre insertedGenre = genreDao.getByName(TEST_GENRE_NAME).orElse(null);
        assertNotNull(insertedGenre);

        genre.setId(1L);
        assertEquals(genre, insertedGenre);
    }
}