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

@Repository
public class AuthorDaoImpl implements AuthorDao {

    private final NamedParameterJdbcOperations ops;

    @Autowired
    public AuthorDaoImpl(NamedParameterJdbcOperations ops) {
        this.ops = ops;
    }


    @Override
    public Optional<Author> insert(Author author) {
        Map<String, Object> params = Collections.singletonMap("name", author.getName());
        ops.update("insert into authors(name) (select :name where not exists(select 1 from authors where name = :name))", params);
        return getByName(author.getName());
    }

    @Override
    public Optional<Author> update(Author author) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", author.getId());
        params.put("name", author.getName());
        ops.update("update authors set name = :name where id = :id", params);
        return getById(author.getId());
    }

    @Override
    public Optional<Author> save(Author author) {
        if (author.getId() != null && author.getId() > 0) {
            return update(author);
        } else {
            return insert(author);
        }
    }

    @Override
    public List<Author> saveList(List<Author> authors) {
        List<Author> savedAuthors = new ArrayList<>();
        for (Author a: authors) {
            save(a).ifPresent(author -> savedAuthors.add(author));
        }
        return savedAuthors.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public boolean remove(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return ops.update("delete from authors where id = :id", params) == 1;
    }

    @Override
    public long getIdByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        Long id = - 1L;
        try {
            id = ops.queryForObject("select id from authors where name = :name", params, Long.class);
        } catch (EmptyResultDataAccessException e) {

        }
        return id;
    }

    @Override
    public Optional<Author> getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            return Optional.ofNullable(ops.queryForObject("select * from authors where id = :id", params, new AuthorRowMapper()));
        } catch (EmptyResultDataAccessException ignored){
        }
        return Optional.empty();
    }

    @Override
    public Optional<Author> getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        try {
            return Optional.ofNullable(ops.queryForObject("select * from authors where name = :name", params, new AuthorRowMapper()));
        } catch (EmptyResultDataAccessException ignored){
        }
        return Optional.empty();
    }
}
