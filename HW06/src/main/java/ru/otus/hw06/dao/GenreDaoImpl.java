package ru.otus.hw06.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hw06.interfaces.dao.GenreDao;
import ru.otus.hw06.models.Genre;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Transactional
public class GenreDaoImpl implements GenreDao {

    @PersistenceContext
    private EntityManager em;

    private final NamedParameterJdbcOperations ops;

    @Autowired
    public GenreDaoImpl(NamedParameterJdbcOperations ops) {
        this.ops = ops;
    }

    @Override
    public Optional<Genre> insert(Genre genre) {
        em.persist(genre);
        em.flush();
        return getByName(genre.getName());
    }

    @Override
    public Optional<Genre> update(Genre genre) {
        em.merge(genre);
        em.flush();
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
        return em.createQuery("delete from Genre g where id = :id").setParameter("id", id).executeUpdate() == 1;

    }

    @Override
    public long getIdByName(String name) {
        Long id = - 1L;
        try {
            id = em.createQuery("select g.id from Genre g where name = :name", Long.class).setParameter("name", name).getSingleResult();
        } catch (NoResultException ignored) {
        }
        return id;
    }

    @Override
    public Optional<Genre> getById(long id) {
        try {
            return Optional.ofNullable(em.createQuery("select g from Genre g where id = :id", Genre.class).setParameter("id", id).getSingleResult());
        } catch (NoResultException ignored){
        }
        return Optional.empty();
    }

    @Override
    public Optional<Genre> getByName(String name) {
        try {
            return Optional.ofNullable(em.createQuery("select g from Genre g where name = :name", Genre.class).setParameter("name", name).getSingleResult());
        } catch (NoResultException ignored){
        }
        return Optional.empty();
    }

}
