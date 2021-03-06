package ru.otus.hw03.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private long id;
    private String text;
    private boolean isRadio;
    private List<QuestionAnswer> answers;
}
