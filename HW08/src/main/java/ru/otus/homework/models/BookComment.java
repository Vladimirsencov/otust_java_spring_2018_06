package ru.otus.homework.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books_comments")
public class BookComment {
    @Id
    private String id;

    private Date commentingTime;

    private String author;

    private String comment;

    @DBRef
    private BookBrief bookBrief;
}
