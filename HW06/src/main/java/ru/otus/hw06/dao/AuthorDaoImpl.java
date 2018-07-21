package ru.otus.hw06.dao;

import org.springframework.stereotype.Repository;
import ru.otus.hw06.interfaces.dao.AuthorDao;
import ru.otus.hw06.models.Author;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Transactional
public class AuthorDaoImpl implements AuthorDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Author> insert(Author author) {
        em.persist(author);
        em.flush();
        return getByName(author.getName());
    }

    @Override
    public Optional<Author> update(Author author) {
        em.merge(author);
        em.flush();
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
        return em.createQuery("delete from Author a where id = :id").setParameter("id", id).executeUpdate() == 1;
    }

    @Override
    public long getIdByName(String name) {
        Long id = - 1L;
        try {
            id = em.createQuery("select a.id from Author a where name = :name", Long.class).setParameter("name", name).getSingleResult();
        } catch (NoResultException ignored) {
        }
        return id;
    }

    @Override
    public Optional<Author> getById(long id) {
        try {
            return Optional.ofNullable(em.createQuery("select a from Author a where id = :id", Author.class).setParameter("id", id).getSingleResult());
        } catch (NoResultException ignored){
        }
        return Optional.empty();
    }

    @Override
    public Optional<Author> getByName(String name) {
        try {
            return Optional.ofNullable(em.createQuery("select a from Author a where name = :name", Author.class).setParameter("name", name).getSingleResult());
        } catch (NoResultException ignored){
        }
        return Optional.empty();
    }
}
