package ru.otus.homework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.interfaces.services.DataStorageService;
import ru.otus.homework.models.BookComment;
import ru.otus.homework.models.dto.BookCommentForWebDto;
import ru.otus.homework.models.dto.OperationExecutionSatusDto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static ru.otus.homework.controllers.MvcConsts.*;

@RestController
public class BookCommentRestController {

    private final DataStorageService dataStorageService;

    @Autowired
    public BookCommentRestController(DataStorageService dataStorageService) {
        this.dataStorageService = dataStorageService;
    }

    @GetMapping(REST_MAPPING_COMMENT)
    public List<BookCommentForWebDto> getCommentsList(@RequestParam long bookId){
        return dataStorageService.getAllBookCommentsByBookId(bookId).stream().map(BookCommentForWebDto::new).collect(Collectors.toList());
    }

    @PostMapping(REST_MAPPING_COMMENT)
    public OperationExecutionSatusDto insertComment(BookCommentForWebDto dto) {
        dataStorageService.getBookBriefById(dto.getBookId())
                .ifPresent(b -> dataStorageService.saveBookComment(new BookComment(dto.getId(), new Date(), dto.getAuthor(), dto.getComment(), b)));
        return new OperationExecutionSatusDto();
    }

    @PutMapping(REST_MAPPING_COMMENT_WITH_ID)
    public OperationExecutionSatusDto updateComment(@PathVariable long id, BookCommentForWebDto dto) {
        dataStorageService.getBookBriefById(dto.getBookId())
                .ifPresent(b -> dataStorageService.saveBookComment(new BookComment(id, new Date(), dto.getAuthor(), dto.getComment(), b)));
        return new OperationExecutionSatusDto();
    }

    @DeleteMapping(REST_MAPPING_COMMENT_WITH_ID)
    public OperationExecutionSatusDto deleteComment(@PathVariable long id) {
        dataStorageService.getBookCommentById(id).ifPresent(c -> dataStorageService.removeBookComment(id));

        return new OperationExecutionSatusDto();
    }

}
