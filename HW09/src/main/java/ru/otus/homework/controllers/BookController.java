package ru.otus.homework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.interfaces.services.DataStorageService;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.BookComment;
import ru.otus.homework.models.dto.BookForWebDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.otus.homework.controllers.ViewConsts.*;

@Controller
public class BookController {


    private final DataStorageService dataStorageService;

    @Autowired
    public BookController(DataStorageService dataStorageService) {
        this.dataStorageService = dataStorageService;
    }

    @RequestMapping("/")
    public String viewBooksList(Model model) {
        List<BookForWebDto> books = dataStorageService.getAllBooks().stream().map(BookForWebDto::new).collect(Collectors.toList());
        model.addAttribute("books", books);
        return VIEW_NAME_BOOKS_LIST;
    }

    @RequestMapping("/book_details")
    public String viewBookDetails(@RequestParam long id, Model model) {
        Optional<Book> bookOptional = dataStorageService.getBookById(id);
        BookForWebDto dto = new BookForWebDto(bookOptional.orElse(new Book()));
        List<BookComment> comments = dataStorageService.getAllBookCommentsByBookId(id);

        model.addAttribute("book", dto);
        model.addAttribute("comments", comments);
        return VIEW_NAME_BOOK_DETAILS;
    }

    @PostMapping("/book/delete")
    public String deleteBook(@RequestParam long id) {
        dataStorageService.removeBook(id);
        return VIEW_NAME_REDIRECT_TO_CONTEXT_PATH;
    }


    @PostMapping("/book/save")
    public String saveBook(@ModelAttribute BookForWebDto dto) {
        Book book = new Book(dto.getId(), dto.getName(), dto.getDescription(), dto.getPubYear(), dto.getAuthorsAsList(), dto.getGenresAsList());
        dataStorageService.saveBook(book);
        return VIEW_NAME_REDIRECT_TO_CONTEXT_PATH;
    }
}
