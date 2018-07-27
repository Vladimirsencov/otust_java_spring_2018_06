package ru.otus.hw06.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

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
}
