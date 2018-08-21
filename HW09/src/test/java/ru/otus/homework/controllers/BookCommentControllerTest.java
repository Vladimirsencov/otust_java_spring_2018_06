package ru.otus.homework.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework.interfaces.services.DataStorageService;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.BookBrief;
import ru.otus.homework.models.BookComment;

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static ru.otus.homework.TestConst.*;
import static ru.otus.homework.controllers.MvcConsts.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BookCommentController.class)
public class BookCommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DataStorageService dataStorageService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void saveComment() throws Exception {
        when(dataStorageService.getBookBriefById(1L)).thenReturn(Optional.of(new BookBrief(1L, TEST_BOOK_NAME)));

        mvc.perform(
                post(REQUEST_COMMENT_SAVE).
                        param("bookId", "1").
                        param("author", TEST_AUTHOR_NAME).
                        param("comment", TEST_COMMENT)
        ).andExpect(view().name(VIEW_NAME_REDIRECT_TO_DETAILS_PAGE + 1));

        // Т.к. время коммента берется во-время сохранения сравнить с заготовленным нельзя. Только с любым
        verify(dataStorageService).saveBookComment(any());

    }

    @Test
    public void deleteComment() throws Exception {
        when(dataStorageService.getBookCommentById(1L)).thenReturn(Optional.of(new BookComment(1L, new Date(), "", "", null)));
        mvc.perform(post(REQUEST_COMMENT_DELETE).param("bookId", "1").param("id", "1"))
                .andExpect(view().name(VIEW_NAME_REDIRECT_TO_DETAILS_PAGE + 1));
        verify(dataStorageService).removeBookComment(1L);

    }
}