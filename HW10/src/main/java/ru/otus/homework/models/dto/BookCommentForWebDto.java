package ru.otus.homework.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.homework.models.BookComment;

import java.text.SimpleDateFormat;

@Data
@AllArgsConstructor
public class BookCommentForWebDto {
    private Long id;
    private Long bookId;
    private String commentingTime;
    private String author;
    private String comment;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public BookCommentForWebDto() {
    }

    public BookCommentForWebDto(BookComment comment) {
        this.id = comment.getId();
        this.bookId = comment.getBookBrief().getId();
        this.author = comment.getAuthor();
        this.comment = comment.getComment();
        this.commentingTime = dateFormat.format(comment.getCommentingTime());
    }


}
