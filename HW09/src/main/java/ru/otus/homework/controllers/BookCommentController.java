package ru.otus.homework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.homework.interfaces.services.DataStorageService;
import ru.otus.homework.models.BookComment;
import ru.otus.homework.models.dto.BookCommentForWebDto;

import java.util.Date;

import static ru.otus.homework.controllers.ViewConsts.*;

@Controller
public class BookCommentController {

    private final DataStorageService dataStorageService;

    @Autowired
    public BookCommentController(DataStorageService dataStorageService) {
        this.dataStorageService = dataStorageService;
    }

    @PostMapping("comment/save")
    public String saveComment(@ModelAttribute BookCommentForWebDto dto) {
        dataStorageService.getBookBriefById(dto.getBookId())
                .ifPresent(b -> dataStorageService.saveBookComment(new BookComment(dto.getId(), new Date(), dto.getAuthor(), dto.getComment(), b)));
        return VIEW_NAME_REDIRECT_TO_DETAILS_PAGE + dto.getBookId();
    }

    @PostMapping("/comment/delete")
    public String deleteComment(@RequestParam long bookId, @RequestParam long id) {
        dataStorageService.getBookCommentById(id).ifPresent(c -> dataStorageService.removeBookComment(id));

        return VIEW_NAME_REDIRECT_TO_DETAILS_PAGE + bookId;
    }

}
