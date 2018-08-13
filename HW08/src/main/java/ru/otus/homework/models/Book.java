package ru.otus.homework.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Data
@AllArgsConstructor
@Document(collection = "books")
public class Book {
    @Id
    private String id;
    private String name;
    private String description;
    private int pubYear;

    @DBRef
    List<Author> authors;

    @DBRef
    List<Genre> genres;

    public Book() {
    }

    public Book(Book book) {
        this.setId(book.getId());
        this.setName(book.getName());
        this.setDescription(book.getDescription());
        this.setPubYear(book.getPubYear());
        this.setAuthors(new ArrayList<>(book.getAuthors()));
        this.setGenres(new ArrayList<>(book.getGenres()));

    }

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
