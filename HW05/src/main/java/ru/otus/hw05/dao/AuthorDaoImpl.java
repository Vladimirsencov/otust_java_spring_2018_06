package ru.otus.hw05.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hw05.dao.mappers.AuthorRowMapper;
import ru.otus.hw05.interfaces.dao.AuthorDao;
import ru.otus.hw05.models.Author;

import java.util.*;
import java.util.stream.Collectors;

import static ru.otus.hw05.dao.DBConsts.*;

@Repository
public class AuthorDaoImpl implements AuthorDao {

    private final NamedParameterJdbcOperations ops;

    @Autowired
    public AuthorDaoImpl(NamedParameterJdbcOperations ops) {
        this.ops = ops;
    }


    @Override
    public Author insert(Author author) {
        Map<String, Object> params = Collections.singletonMap(F_NAME, author.getName());
        ops.update(String.format(TEMPLATE_INSERT_ID_NAME_REC_SQL, TBL_AUTHORS, F_NAME), params);
        return getByName(author.getName());
    }

    @Override
    public Author update(Author author) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", author.getId());
        params.put("name", author.getName());
        ops.update(String.format(TEMPLATE_UPDATE_ID_NAME_REC_SQL, TBL_AUTHORS, F_NAME, F_ID), params);
        return getById(author.getId());
    }

    @Override
    public Author save(Author author) {
        if (author.getId() != null && author.getId() > 0) {
            return update(author);
        } else {
            return insert(author);
        }
    }

    @Override
    public List<Author> save(List<Author> authors) {
        List<Author> savedAuthors = new ArrayList<>();
        for (Author a: authors) {
            savedAuthors.add(save(a));
        }
        return savedAuthors.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public boolean remove(long id) {
        Map<String, Object> params = Collections.singletonMap(F_ID, id);
        return ops.update(String.format(TEMPLATE_REMOVE_BY_ID_SQL, TBL_AUTHORS, F_ID, F_ID), params) == 1;
    }

    @Override
    public long getIdByName(String name) {
        Map<String, Object> params = Collections.singletonMap(F_NAME, name);
        Long id = - 1L;
        try {
            id = ops.queryForObject(String.format(TEMPLATE_SELECT_WITH_ONE_CONDITION_SQL, F_ID, TBL_AUTHORS, F_NAME, F_NAME), params, Long.class);
        } catch (EmptyResultDataAccessException e) {

        }
        return id;
    }

    @Override
    public Author getById(long id) {
        Map<String, Object> params = Collections.singletonMap(F_ID, id);
        try {
            return ops.queryForObject(String.format(TEMPLATE_SELECT_WITH_ONE_CONDITION_SQL, "*", TBL_AUTHORS, F_ID, F_ID), params, new AuthorRowMapper());
        } catch (EmptyResultDataAccessException ignored){
        }
        return null;
    }

    @Override
    public Author getByName(String name) {
        Map<String, Object> params = Collections.singletonMap(F_NAME, name);
        try {
            return ops.queryForObject(String.format(TEMPLATE_SELECT_WITH_ONE_CONDITION_SQL, "*", TBL_AUTHORS, F_NAME, F_NAME), params, new AuthorRowMapper());
        } catch (EmptyResultDataAccessException ignored){
        }
        return null;
    }
}
