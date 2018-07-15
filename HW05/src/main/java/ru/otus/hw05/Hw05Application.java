package ru.otus.hw05;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.hw05.interfaces.dao.AuthorDao;
import ru.otus.hw05.interfaces.dao.BookDao;
import ru.otus.hw05.interfaces.dao.GenreDao;
import ru.otus.hw05.models.Author;
import ru.otus.hw05.models.Book;
import ru.otus.hw05.models.Genre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class Hw05Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Hw05Application.class, args);

        AuthorDao authorDao = ctx.getBean(AuthorDao.class);
        GenreDao genreDao = ctx.getBean(GenreDao.class);
        BookDao bookDao = ctx.getBean(BookDao.class);
/*
        Author author = new Author(null, "Vasya");
        author = authorDao.save(author);
        System.out.println(author);

        author.setName("Igor");
        author = authorDao.save(author);
        System.out.println(author);


        Genre genre = new Genre(null, "Comedy");
        genre = genreDao.save(genre);
        System.out.println(genre);

        genre.setName("Fantasy");
        genre = genreDao.save(genre);
        System.out.println(genre);
*/

        List<Author> authors = new ArrayList<>();
        authors.add(new Author(null, "Vasya"));
        authors.add(new Author(null, "Igor"));

        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(null, "Comedy"));
        genres.add(new Genre(null, "Fantasy"));
        Book book = new Book(null, "book1", "none", 2005, authors, genres);
        book = bookDao.save(book);
        System.out.println(book);

        book.setName("book2");
        book.setDescription("any");
        book.setPubYear(2011);
        book.getAuthors().add(new Author(null, "Petya"));
        book.getGenres().add(new Genre(null, "Erotic"));
        book = bookDao.save(book);
        System.out.println(book);

        authors = Collections.singletonList(new Author(null, "Evgeny"));
        genres = Collections.singletonList(new Genre(null, "Detective"));
        book = new Book(null, "book3", "none", 2005, authors, genres);
        bookDao.save(book);
        List<Book> books = bookDao.getAll();
        System.out.println(books);

    }
}
