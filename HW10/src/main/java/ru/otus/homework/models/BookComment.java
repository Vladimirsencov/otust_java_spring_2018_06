package ru.otus.homework.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books_comments")
public class BookComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "commenting_time")
    private Date commentingTime;

    @Column(name = "author")
    private String author;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(targetEntity = BookBrief.class)
    @JoinColumn(name = "book_id")
    private BookBrief bookBrief;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookComment comment1 = (BookComment) o;
        return Objects.equals(id, comment1.id) &&
                Objects.equals(commentingTime.getTime(), comment1.commentingTime.getTime()) &&
                Objects.equals(author, comment1.author) &&
                Objects.equals(comment, comment1.comment) &&
                Objects.equals(bookBrief, comment1.bookBrief);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, commentingTime, author, comment, bookBrief);
    }
}
