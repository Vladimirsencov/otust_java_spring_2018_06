package ru.otus.hw03.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAnswer {
    private long id;
    private String text;
    private boolean isCorrect;
}
