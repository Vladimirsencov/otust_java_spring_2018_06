package ru.otus.hw06.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private int pubYear;


    @ManyToMany(targetEntity = Author.class, cascade = CascadeType.ALL)
    @JoinTable(name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    List<Author> authors;

    @ManyToMany(targetEntity = Genre.class, cascade = CascadeType.ALL)
    @JoinTable(name = "books_genres",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"))
    @LazyCollection(LazyCollectionOption.FALSE)
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
        return Objects.hashCode(this);
    }
}
