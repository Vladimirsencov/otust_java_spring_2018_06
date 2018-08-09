package ru.otus.homework.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class Book {
    @Id
    private String id;
    private String name;
    private String description;
    private int pubYear;

    List<Author> authors;

    List<Genre> genres;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Book)) return false;

        Book book = (Book) o;

        if (!Objects.equals(pubYear, book.pubYear)) return false;
        if (!Objects.equals(id, book.id)) return false;
        if (!Objects.equals(name, book.name)) return false;
        if (!Objects.equals(description, book.description)) return false;
        if (!Arrays.equals(authors != null ? authors.toArray() : new Author[0], book.authors != null? book.authors.toArray(): new Author[0])) return false;
        return Arrays.equals(genres != null? genres.toArray(): new Genre[0], book.genres != null? book.genres.toArray(): new Genre[0]);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this);
    }
}
