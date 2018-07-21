package ru.otus.hw06.dao;

import org.springframework.stereotype.Repository;
import ru.otus.hw06.interfaces.dao.BookCommentDao;
import ru.otus.hw06.models.BookComment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class BookCommentDaoImpl implements BookCommentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<BookComment> insert(BookComment comment) {
        em.persist(comment);
        em.flush();
        return Optional.ofNullable(comment);
    }

    @Override
    public boolean remove(long id) {
        return em.createQuery("delete from BookComment c where id = :id").setParameter("id", id).executeUpdate() == 1;
    }

    @Override
    public List<BookComment> getAllByBookId(long bookId) {
        return em.createQuery("select c from BookComment c where c.bookBrief.id = :book_id order by c.commentingTime", BookComment.class)
                .setParameter("book_id", bookId)
                .getResultList();
    }
}
