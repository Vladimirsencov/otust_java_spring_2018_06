package ru.otus.hw05.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    Long id;
    String name;
    String description;
    int pubYear;

    List<Author> authors;
    List<Genre> genres;

}
