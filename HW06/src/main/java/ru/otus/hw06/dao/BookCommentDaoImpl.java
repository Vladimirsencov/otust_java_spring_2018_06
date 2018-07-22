package ru.otus.hw06.dao;

import org.springframework.stereotype.Repository;
import ru.otus.hw06.interfaces.dao.BookCommentDao;
import ru.otus.hw06.models.BookComment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class BookCommentDaoImpl implements BookCommentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public BookComment insert(BookComment comment) {
        em.persist(comment);
        return comment;
    }

    @Override
    public void remove(long id) {
        em.createQuery("delete from BookComment c where id = :id").setParameter("id", id).executeUpdate();
    }

    @Override
    public List<BookComment> getAllByBookId(long bookId) {
        return em.createQuery("select c from BookComment c where c.bookBrief.id = :book_id order by c.commentingTime", BookComment.class)
                .setParameter("book_id", bookId)
                .getResultList();
    }
}
