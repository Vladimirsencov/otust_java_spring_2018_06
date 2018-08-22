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

import static ru.otus.homework.controllers.MvcConsts.*;

@Controller
public class BookController {


    private final DataStorageService dataStorageService;

    @Autowired
    public BookController(DataStorageService dataStorageService) {
        this.dataStorageService = dataStorageService;
    }

    @GetMapping("/")
    public String viewBooksList() {
        return VIEW_NAME_BOOKS_LIST;
    }

    @RequestMapping(REQUEST_BOOK_DETAILS)
    public String viewBookDetails(@RequestParam long id, Model model) {
        Optional<Book> bookOptional = dataStorageService.getBookById(id);
        BookForWebDto dto = new BookForWebDto(bookOptional.orElse(new Book()));
        model.addAttribute(MODEL_ATTR_BOOK, dto);
        return VIEW_NAME_BOOK_DETAILS;
    }
}
