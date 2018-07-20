package ru.otus.hw05.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hw05.dao.mappers.GenreRowMapper;
import ru.otus.hw05.interfaces.dao.GenreDao;
import ru.otus.hw05.models.Genre;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class GenreDaoImpl implements GenreDao {

    private final NamedParameterJdbcOperations ops;

    @Autowired
    public GenreDaoImpl(NamedParameterJdbcOperations ops) {
        this.ops = ops;
    }

    @Override
    public Optional<Genre> insert(Genre genre) {
        Map<String, Object> params = Collections.singletonMap("name", genre.getName());
        ops.update("insert into genres(name) (select :name where not exists(select 1 from genres where name = :name))", params);
        return getByName(genre.getName());
    }

    @Override
    public Optional<Genre> update(Genre genre) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", genre.getId());
        params.put("name", genre.getName());
        ops.update("update genres set name = :name where id = :id", params);
        return getById(genre.getId());

    }

    @Override
    public Optional<Genre> save(Genre genre) {
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
            save(g).ifPresent(genre -> savedGenres.add(genre));
        }
        return savedGenres.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public boolean remove(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return ops.update("delete from genres where id = :id", params) == 1;

    }

    @Override
    public long getIdByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        Long id = - 1L;
        try {
            id = ops.queryForObject("select id from genres where name = :name", params, Long.class);
        } catch (EmptyResultDataAccessException ignored){
        }
        return id;
    }

    @Override
    public Optional<Genre> getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            return Optional.ofNullable(ops.queryForObject("select * from genres where id = :id", params, new GenreRowMapper()));
        } catch (EmptyResultDataAccessException ignored){
        }
        return Optional.empty();
    }

    @Override
    public Optional<Genre> getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        try {
            return Optional.ofNullable(ops.queryForObject("select * from genres where name = :name", params, new GenreRowMapper()));
        } catch (EmptyResultDataAccessException ignored){
        }
        return Optional.empty();
    }

}
