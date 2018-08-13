package ru.otus.homework.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.interfaces.i18n.MessageSourceWrapper;
import ru.otus.homework.interfaces.services.DataStorageService;
import ru.otus.homework.models.*;

import java.util.List;
import java.util.stream.Collectors;

import static ru.otus.homework.shell.ShellConst.*;

@ShellComponent
public class BookCommands {
    private static final String ERR_WRONG_NAME_LENGTH = "err.wrong.name.length";
    private static final String ERR_WRONG_DESCRIPTION_LENGTH = "err.wrong.description.length";
    private static final String ERR_WRONG_PUB_YEAR = "err.wrong.pub.year";
    private static final String BOOK_OUTPUT_PATTERN = "book.output.pattern";

    private static final int MIN_NAME_LEN = 3;
    private static final int MIN_DESCRIPTION_LEN = 15;
    private static final int MIN_PUB_YEAR = 1500;
    private static final int EMPTY_YEAR = 0;


    private final DataStorageService dataStorageService;
    private final ShellParametersConverter paramsConverter;
    private final MessageSourceWrapper messageSourceWrapper;

    @Autowired
    public BookCommands(DataStorageService dataStorageService, ShellParametersConverter paramsConverter, MessageSourceWrapper messageSourceWrapper) {
        this.dataStorageService = dataStorageService;
        this.paramsConverter = paramsConverter;
        this.messageSourceWrapper = messageSourceWrapper;
    }

    @ShellMethod("AddBook")
    public void addBook(@ShellOption String name, @ShellOption String description, @ShellOption int pubYear, @ShellOption String authors, @ShellOption String genres) throws Exception {
        if (!checkCommandParameters(name, description, pubYear)) {
            return;
        }
        Book book = new Book(null, name, description, pubYear, paramsConverter.authorsString2List(authors), paramsConverter.genresString2List(genres));
        dataStorageService.saveBook(book);
    }

    @ShellMethod("UpdateBook")
    public void updateBook(@ShellOption String bookId,
                           @ShellOption(defaultValue = ShellOption.NULL) String name,
                           @ShellOption(defaultValue = ShellOption.NULL) String description,
                           @ShellOption(defaultValue = "0") int pubYear,
                           @ShellOption(defaultValue = ShellOption.NULL) String authors,
                           @ShellOption(defaultValue = ShellOption.NULL) String genres) {

        if (!checkCommandParameters(name, description, pubYear)) {
            return;
        }

        dataStorageService.getBookById(bookId).map(b -> dataStorageService.saveBook(applyCommandParameters2Book(b, name, description, pubYear, authors, genres)))
                .orElseGet(() -> {
                    System.err.println(messageSourceWrapper.getMsg(ERR_BOOK_NOT_FOUND));
                    return null;
                });
    }

    @ShellMethod("RemoveBook")
    public void removeBook(@ShellOption String bookId) {
        dataStorageService.removeBook(bookId);
    }

    @ShellMethod("ListBooks")
    public String listBooks() {
        List<Book> books = dataStorageService.getAllBooks();
        StringBuilder builder = new StringBuilder();
        books.forEach(book -> {
            builder.append(messageSourceWrapper.getMsg(BOOK_OUTPUT_PATTERN, book.getId(),
                    book.getName(),
                    book.getDescription(),
                    String.valueOf(book.getPubYear()),
                    book.getAuthors().stream().map(Author::getName).collect(Collectors.joining(", ")),
                    book.getGenres().stream().map(Genre::getName).collect(Collectors.joining(", "))
                    )
            );
            builder.append(CONTENT_DELIMITER);
        });
        return builder.toString();
    }

    private boolean checkCommandParameters(String name, String description, int pubYear) {
        if (name != null && name.length() < MIN_NAME_LEN) {
            System.err.println(messageSourceWrapper.getMsg(ERR_WRONG_NAME_LENGTH));
            return false;
        }

        if (description != null && description.length() < MIN_DESCRIPTION_LEN) {
            System.err.println(messageSourceWrapper.getMsg(ERR_WRONG_DESCRIPTION_LENGTH));
            return false;
        }

        if (pubYear != EMPTY_YEAR && pubYear < MIN_PUB_YEAR) {
            System.err.println(messageSourceWrapper.getMsg(ERR_WRONG_PUB_YEAR));
            return false;
        }

        return true;
    }

    private Book applyCommandParameters2Book(Book book, String name, String description, int pubYear, String authors, String genres) {
        if (name != null) {
            book.setName(name);
        }
        if (description != null) {
            book.setDescription(description);
        }
        if (pubYear > 0) {
            book.setPubYear(pubYear);
        }
        if (authors != null) {
            book.setAuthors(paramsConverter.authorsString2List(authors));
        }
        if (genres != null) {
            book.setGenres(paramsConverter.genresString2List(genres));
        }

        return book;
    }
}
