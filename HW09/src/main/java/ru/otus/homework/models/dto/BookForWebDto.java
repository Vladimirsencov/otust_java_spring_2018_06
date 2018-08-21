package ru.otus.homework.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class BookForWebDto {
    private static final int MAX_SHORT_DESCRIPTION_LENGTH = 105;
    private Long id;
    private String name;
    private String description;
    private int pubYear;
    private String authors;
    private String genres;

    public BookForWebDto() {
    }

    public BookForWebDto(Book book) {
        id = book.getId();
        name = book.getName();
        description = book.getDescription();
        pubYear = book.getPubYear();
        authors = Optional.ofNullable(book.getAuthors()).map(lst -> lst.stream().map(Author::getName).collect(Collectors.joining(", "))).orElse("");
        genres = Optional.ofNullable(book.getGenres()).map(lst -> lst.stream().map(Genre::getName).collect(Collectors.joining(", "))).orElse("");
    }

    public String getShortDescription() {
        return description.length() > MAX_SHORT_DESCRIPTION_LENGTH ? description.substring(0, MAX_SHORT_DESCRIPTION_LENGTH - 5) + "...": description;
    }

    public List<Author> getAuthorsAsList() {
        if (authors == null) {
            return null;
        }
        return Arrays.stream(authors.split(",")).map(name -> new Author(null, name)).collect(Collectors.toList());
    }

    public List<Genre> getGenresAsList() {
        if (genres == null) {
            return null;
        }
        return Arrays.stream(genres.split(",")).map(name -> new Genre(null, name)).collect(Collectors.toList());
    }
}
