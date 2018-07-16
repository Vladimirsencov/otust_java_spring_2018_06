package ru.otus.hw05.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.hw05.interfaces.dao.GenreDao;
import ru.otus.hw05.models.Genre;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GenreDaoImplTest {
    private static final String TEMP_GENRE_NAME = "Comedy";
    private static final String TEMP_GENRE_NAME2 = "Erotic";

    @Autowired
    private GenreDao genreDao;

    @Test
    public void insert() throws Exception {
        Genre Genre = new Genre(null, TEMP_GENRE_NAME);
        Genre insertedGenre = genreDao.insert(Genre);
        assertNotNull(insertedGenre);
        assertNotNull(insertedGenre.getId());
        long id = insertedGenre.getId();
        assertEquals(1L, id);
    }

    @Test
    public void update() throws Exception {
        Genre Genre = new Genre(null, TEMP_GENRE_NAME);
        Genre = genreDao.insert(Genre);
        Genre.setName(TEMP_GENRE_NAME2);
        Genre updatedGenre = genreDao.update(Genre);
        assertNotNull(updatedGenre);
        assertEquals(TEMP_GENRE_NAME2, updatedGenre.getName());
    }

    @Test
    public void save() throws Exception {
        Genre Genre = new Genre(null, TEMP_GENRE_NAME);
        Genre insertedGenre = genreDao.save(Genre);
        assertNotNull(insertedGenre);
        assertNotNull(insertedGenre.getId());
        long id = insertedGenre.getId();
        assertEquals(1L, id);

        insertedGenre.setName(TEMP_GENRE_NAME2);
        Genre updatedGenre = genreDao.save(insertedGenre);
        assertNotNull(updatedGenre);
        assertEquals(TEMP_GENRE_NAME2, updatedGenre.getName());
    }

    @Test
    public void saveListOfGenres() throws Exception {
        List<Genre> Genres = new ArrayList<>();
        Genres.add(new Genre(null, TEMP_GENRE_NAME));
        Genres.add(new Genre(null, TEMP_GENRE_NAME2));

        List<Genre> insertedGenres = genreDao.save(Genres);
        Genres.get(0).setId(1L);
        Genres.get(1).setId(2L);

        assertEquals(Genres, insertedGenres);
    }

    @Test
    public void remove() throws Exception {
        Genre Genre = new Genre(null, TEMP_GENRE_NAME);
        genreDao.save(Genre);
        Genre = genreDao.getById(1L);
        assertNotNull(Genre);
        genreDao.remove(1L);
        Genre = genreDao.getById(1L);
        assertNull(Genre);
    }

    @Test
    public void getIdByName() throws Exception {
        long id = genreDao.getIdByName(TEMP_GENRE_NAME);
        assertEquals(- 1L, id);

        Genre Genre = new Genre(null, TEMP_GENRE_NAME);
        genreDao.save(Genre);

        id = genreDao.getIdByName(TEMP_GENRE_NAME);
        assertEquals(1L, id);
    }

    @Test
    public void getById() throws Exception {
        Genre Genre = new Genre(null, TEMP_GENRE_NAME);
        genreDao.save(Genre);
        Genre insertedGenre = genreDao.getById(1L);
        assertNotNull(insertedGenre);

        Genre.setId(1L);
        assertEquals(Genre, insertedGenre);
    }

    @Test
    public void getByName() throws Exception {
        Genre Genre = new Genre(null, TEMP_GENRE_NAME);
        genreDao.save(Genre);
        Genre insertedGenre = genreDao.getByName(TEMP_GENRE_NAME);
        assertNotNull(insertedGenre);

        Genre.setId(1L);
        assertEquals(Genre, insertedGenre);
    }
}