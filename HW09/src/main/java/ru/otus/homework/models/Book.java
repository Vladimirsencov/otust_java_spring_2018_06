package ru.otus.homework.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
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
