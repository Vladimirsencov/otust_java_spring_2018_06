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
import ru.otus.homework.models.BookBrief;
import ru.otus.homework.models.BookComment;
import ru.otus.homework.models.dto.BookCommentForWebDto;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.otus.homework.TestConst.*;
import static ru.otus.homework.controllers.MvcConsts.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BookCommentRestController.class)
public class BookCommentRestControllerTest {
    private static final String COMMENT_DTO_JSON_TEMPLATE = "[{\"id\":%d, \"bookId\":%d, \"commentingTime\":\"%s\", \"author\":\"%s\", \"comment\":\"%s\"}]";
    private static final String PARAM_AUTHOR = "author";
    private static final String PARAM_COMMENT = "comment";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DataStorageService dataStorageService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getCommentsList() throws Exception {
        BookComment comment = new BookComment(TEST_ID_LONG, new Date(), TEST_AUTHOR_NAME, TEST_COMMENT, new BookBrief(TEST_ID_LONG, TEST_BOOK_NAME));
        BookCommentForWebDto commentDto = new BookCommentForWebDto(comment);

        String expectedJson = String.format(COMMENT_DTO_JSON_TEMPLATE, commentDto.getId(),
                commentDto.getBookId(),
                commentDto.getCommentingTime(),
                commentDto.getAuthor(),
                commentDto.getComment());

        when(dataStorageService.getAllBookCommentsByBookId(TEST_ID_LONG)).thenReturn(Collections.singletonList(comment));
        mvc.perform(get(REST_MAPPING_COMMENT).param(PARAM_BOOK_ID, "1")).andExpect(status().isOk()).andExpect(content().json(expectedJson));
    }

    @Test
    public void saveComment() throws Exception {
        when(dataStorageService.getBookBriefById(TEST_ID_LONG)).thenReturn(Optional.of(new BookBrief(TEST_ID_LONG, TEST_BOOK_NAME)));

        mvc.perform(
                post(REST_MAPPING_COMMENT)
                        .param(PARAM_BOOK_ID, TEST_ID_STRING)
                        .param(PARAM_AUTHOR, TEST_AUTHOR_NAME)
                        .param(PARAM_COMMENT, TEST_COMMENT)
        ).andExpect(content().json(OPERATION_STATUS_OK));

        mvc.perform(
                put(REST_MAPPING_COMMENT + TEST_ID_STRING)
                        .param(PARAM_ID, TEST_ID_STRING)
                        .param(PARAM_BOOK_ID, TEST_ID_STRING)
                        .param(PARAM_AUTHOR, TEST_AUTHOR_NAME)
                        .param(PARAM_COMMENT, TEST_COMMENT)
        ).andExpect(content().json(OPERATION_STATUS_OK));

        verify(dataStorageService, times(2)).saveBookComment(any());

    }

    @Test
    public void deleteComment() throws Exception {
        when(dataStorageService.getBookCommentById(TEST_ID_LONG)).thenReturn(Optional.of(new BookComment(TEST_ID_LONG, new Date(), "", "", null)));
        mvc.perform(delete(REST_MAPPING_COMMENT + TEST_ID_STRING).param(PARAM_ID, TEST_ID_STRING))
                .andExpect(content().json(OPERATION_STATUS_OK));
        verify(dataStorageService).removeBookComment(TEST_ID_LONG);
    }
}