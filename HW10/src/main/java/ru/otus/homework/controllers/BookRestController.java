package ru.otus.homework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.interfaces.services.DataStorageService;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.dto.BookForWebDto;
import ru.otus.homework.models.dto.OperationExecutionSatusDto;

import java.util.List;
import java.util.stream.Collectors;

import static ru.otus.homework.controllers.MvcConsts.*;

@RestController
public class BookRestController {
    private final DataStorageService dataStorageService;

    @Autowired
    public BookRestController(DataStorageService dataStorageService) {
        this.dataStorageService = dataStorageService;
    }

    @GetMapping(REST_MAPPING_BOOK)
    public List<BookForWebDto> getBooksList() {
        List<BookForWebDto> books = dataStorageService.getAllBooks().stream().map(BookForWebDto::new).collect(Collectors.toList());
        return books;
    }

    @PostMapping(REST_MAPPING_BOOK)
    public OperationExecutionSatusDto insertBook(BookForWebDto dto) {
        Book book = new Book(dto.getId(), dto.getName(), dto.getDescription(), dto.getPubYear(), dto.getAuthorsAsList(), dto.getGenresAsList());
        dataStorageService.saveBook(book);
        return new OperationExecutionSatusDto();
    }

    @DeleteMapping(REST_MAPPING_BOOK_WITH_ID)
    public OperationExecutionSatusDto deleteBook(@PathVariable long id) {
        dataStorageService.removeBook(id);
        return new OperationExecutionSatusDto();
    }

    @PutMapping(REST_MAPPING_BOOK_WITH_ID)
    public OperationExecutionSatusDto updateBook(@PathVariable long id, BookForWebDto dto) {
        Book book = new Book(id, dto.getName(), dto.getDescription(), dto.getPubYear(), dto.getAuthorsAsList(), dto.getGenresAsList());
        dataStorageService.saveBook(book);
        return new OperationExecutionSatusDto();
    }


}
