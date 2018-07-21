package ru.otus.hw06.dao;

import org.springframework.stereotype.Repository;
import ru.otus.hw06.interfaces.dao.BookDao;
import ru.otus.hw06.models.Book;
import ru.otus.hw06.models.BookBrief;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;

@Repository
@Transactional
public class BookDaoImpl implements BookDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Book> insert(Book book) {
        em.persist(book);
        em.flush();
        return Optional.ofNullable(book);
    }

    @Override
    public Optional<Book> update(Book book) {
        book = em.merge(book);
        em.flush();
        return Optional.of(book);
    }

    @Override
    public Optional<Book> save(Book book) {
        if (book.getId() != null && book.getId() > 0) {
            return update(book);
        } else {
            return insert(book);
        }

    }

    @Override
    public boolean remove(long id) {
        return em.createQuery("delete from Book b where id = :id").setParameter("id", id).executeUpdate() == 1;
    }

    @Override
    public Optional<Book> getById(long id) {
        try {
            return Optional.ofNullable(em.createQuery("select b from Book b where id = :id", Book.class).setParameter("id", id).getSingleResult());
        } catch (NoResultException ignored){
        }
        return Optional.empty();
    }

    @Override
    public Optional<Long> getIdByNameAndDescription(String name, String description) {
        try {
            return Optional.ofNullable(em.createQuery("select b.id from Book b where name = :name and description = :description", Long.class)
                    .setParameter("name", name)
                    .setParameter("description", description)
                    .getSingleResult());
        } catch (NoResultException ignored){
        }
        return Optional.empty();
    }

    @Override
    public List<Book> getAll() {
        return em.createQuery("select b from Book b order by b.pubYear, b.name", Book.class).getResultList();
    }

    @Override
    public Optional<BookBrief> getBookBriefById(long id) {
        try {
            return Optional.ofNullable(em.createQuery("select b from BookBrief b where b.id = :id", BookBrief.class).setParameter("id", id).getSingleResult());
        } catch (NoResultException ignored){
        }
        return Optional.empty();
    }
}
