package ru.otus.hw01.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Test {
    private long id;
    private String name;
    private int correctQuestionsToPass;
    private List<Question> questions;
}
