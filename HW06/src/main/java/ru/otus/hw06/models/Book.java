package ru.otus.hw06.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
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
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (pubYear != book.pubYear) return false;
        if (id != null ? !id.equals(book.id) : book.id != null) return false;
        if (name != null ? !name.equals(book.name) : book.name != null) return false;
        if (description != null ? !description.equals(book.description) : book.description != null) return false;

        if ((authors == null && book.authors != null) || (authors != null && book.authors == null)) return false;
        if (authors != null && book.authors != null && authors.size() != book.authors.size()) return false;

        if ((genres == null && book.genres != null) || (genres != null && book.genres == null)) return false;
        if (genres != null && book.genres != null && genres.size() != book.genres.size()) return false;

        for (int i = 0; i < authors.size(); i++) {
            if (!authors.get(i).equals(book.authors.get(i))) return false;
        }

        for (int i = 0; i < genres.size(); i++) {
            if (!genres.get(i).equals(book.genres.get(i))) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 31 + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + pubYear;
        result = 31 * result + (authors != null ? authors.hashCode() : 0);
        result = 31 * result + (genres != null ? genres.hashCode() : 0);
        return result;
    }
}
