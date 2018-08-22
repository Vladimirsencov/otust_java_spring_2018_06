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
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;
import ru.otus.homework.models.dto.BookForWebDto;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.otus.homework.TestConst.*;
import static ru.otus.homework.controllers.MvcConsts.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BookRestController.class)
public class BookRestControllerTest {

    private static final String BOOK_DTO_JSON_TEMPLATE = "[{\"id\":%d, \"name\":\"%s\", \"description\":\"%s\", \"pubYear\":%d, \"authors\":\"%s\", \"genres\":\"%s\"}]";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_DESCRIPTION = "description";
    private static final String PARAM_PUB_YEAR = "pubYear";
    private static final String PARAM_AUTHORS = "authors";
    private static final String PARAM_GENRES = "genres";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DataStorageService dataStorageService;

    private Book testBook;
    private BookForWebDto testBookDto;

    @Before
    public void setUp() throws Exception {
        testBook = new Book(TEST_ID_LONG, TEST_BOOK_NAME, TEST_BOOK_DESC, TEST_BOOK_PUB_YEAR,
                Collections.singletonList(new Author(null, TEST_AUTHOR_NAME)),
                Collections.singletonList(new Genre(null, TEST_GENRE_NAME)));
        testBookDto = new BookForWebDto(testBook);
    }

    @Test
    public void getBooksList() throws Exception {
        String expectedJson = String.format(BOOK_DTO_JSON_TEMPLATE, testBookDto.getId(), testBookDto.getName(),
                testBookDto.getDescription(), testBookDto.getPubYear(), testBookDto.getAuthors(), testBookDto.getGenres());

        when(dataStorageService.getAllBooks()).thenReturn(Collections.singletonList(testBook));
        mvc.perform(get(REST_MAPPING_BOOK)).andExpect(status().isOk()).andExpect(content().json(expectedJson));

    }

    @Test
    public void updateBook() throws Exception {
        mvc.perform(
                put(REST_MAPPING_BOOK + TEST_ID_STRING)
                        .param(PARAM_ID, TEST_ID_STRING)
                        .param(PARAM_NAME, testBookDto.getName())
                        .param(PARAM_DESCRIPTION, testBookDto.getDescription())
                        .param(PARAM_PUB_YEAR, String.valueOf(testBookDto.getPubYear()))
                        .param(PARAM_AUTHORS, testBookDto.getAuthors())
                        .param(PARAM_GENRES, testBookDto.getGenres())
        ).andExpect(status().isOk()).andExpect(content().json(OPERATION_STATUS_OK));

        verify(dataStorageService).saveBook(testBook);
    }

    @Test
    public void insertBook() throws Exception {
        mvc.perform(
                post(REST_MAPPING_BOOK)
                        .param(PARAM_NAME, testBookDto.getName())
                        .param(PARAM_DESCRIPTION, testBookDto.getDescription())
                        .param(PARAM_PUB_YEAR, String.valueOf(testBookDto.getPubYear()))
                        .param(PARAM_AUTHORS, testBookDto.getAuthors())
                        .param(PARAM_GENRES, testBookDto.getGenres())
        ).andExpect(status().isOk()).andExpect(content().json(OPERATION_STATUS_OK));

        testBook.setId(null);
        verify(dataStorageService).saveBook(testBook);
    }

    @Test
    public void deleteBook() throws Exception {
        mvc.perform(delete(REST_MAPPING_BOOK + TEST_ID_STRING).param(PARAM_ID, TEST_ID_STRING))
                .andExpect(content().json(OPERATION_STATUS_OK));
        verify(dataStorageService).removeBook(TEST_ID_LONG);

    }
}