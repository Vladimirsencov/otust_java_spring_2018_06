package ru.otus.hw05.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hw05.dao.mappers.GenreRowMapper;
import ru.otus.hw05.interfaces.dao.GenreDao;
import ru.otus.hw05.models.Genre;

import java.util.*;
import java.util.stream.Collectors;

import static ru.otus.hw05.dao.DBConsts.*;

@Repository
public class GenreDaoImpl implements GenreDao {

    private final NamedParameterJdbcOperations ops;

    @Autowired
    public GenreDaoImpl(NamedParameterJdbcOperations ops) {
        this.ops = ops;
    }

    @Override
    public Genre insert(Genre genre) {
        Map<String, Object> params = Collections.singletonMap(F_NAME, genre.getName());
        ops.update(String.format(TEMPLATE_INSERT_ID_NAME_REC_SQL, TBL_GENRES, F_NAME), params);
        return getByName(genre.getName());
    }

    @Override
    public Genre update(Genre genre) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", genre.getId());
        params.put("name", genre.getName());
        ops.update(String.format(TEMPLATE_UPDATE_ID_NAME_REC_SQL, TBL_GENRES, F_NAME, F_ID), params);
        return getById(genre.getId());

    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() != null && genre.getId() > 0) {
            return update(genre);
        } else {
            return insert(genre);
        }
    }

    @Override
    public List<Genre> save(List<Genre> genres) {
        List<Genre> savedGenres = new ArrayList<>();
        for (Genre g: genres) {
            savedGenres.add(save(g));
        }
        return savedGenres.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public boolean remove(long id) {
        Map<String, Object> params = Collections.singletonMap(F_ID, id);
        return ops.update(String.format(TEMPLATE_REMOVE_BY_ID_SQL, TBL_GENRES, F_ID, F_ID), params) == 1;

    }

    @Override
    public long getIdByName(String name) {
        Map<String, Object> params = Collections.singletonMap(F_NAME, name);
        Long id = ops.queryForObject(String.format(TEMPLATE_SELECT_WITH_ONE_CONDITION_SQL, F_ID, TBL_GENRES, F_NAME, F_NAME), params, Long.class);
        return id == null? - 1: id;
    }

    @Override
    public Genre getById(long id) {
        Map<String, Object> params = Collections.singletonMap(F_ID, id);
        return ops.queryForObject(String.format(TEMPLATE_SELECT_WITH_ONE_CONDITION_SQL, "*", TBL_GENRES, F_ID, F_ID), params, new GenreRowMapper());

    }

    @Override
    public Genre getByName(String name) {
        Map<String, Object> params = Collections.singletonMap(F_NAME, name);
        return ops.queryForObject(String.format(TEMPLATE_SELECT_WITH_ONE_CONDITION_SQL, "*", TBL_GENRES, F_NAME, F_NAME), params, new GenreRowMapper());
    }

}
