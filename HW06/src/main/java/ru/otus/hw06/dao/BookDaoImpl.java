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
    public Book insert(Book book) {
        em.persist(book);
        return book;
    }

    @Override
    public Book update(Book book) {
        book = em.merge(book);
        return book;
    }

    @Override
    public Book save(Book book) {
        if (book.getId() != null && book.getId() > 0) {
            return update(book);
        } else {
            return insert(book);
        }

    }

    @Override
    public void remove(long id) {
        em.createQuery("delete from Book b where id = :id").setParameter("id", id).executeUpdate();
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
