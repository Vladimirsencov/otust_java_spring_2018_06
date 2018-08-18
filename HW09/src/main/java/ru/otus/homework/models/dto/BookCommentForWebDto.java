package ru.otus.homework.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookCommentForWebDto {
    private Long id;
    private Long bookId;
    private String author;
    private String comment;
}
