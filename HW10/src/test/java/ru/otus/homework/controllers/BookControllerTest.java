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

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.homework.TestConst.*;
import static ru.otus.homework.controllers.MvcConsts.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DataStorageService dataStorageService;

    private Book testBook;

    @Before
    public void setUp() throws Exception {
        testBook = new Book(null, TEST_BOOK_NAME, TEST_BOOK_DESC, TEST_BOOK_PUB_YEAR, null, null);
    }

    @Test
    public void viewBooksList() throws Exception {
        when(dataStorageService.getAllBooks()).thenReturn(Collections.singletonList(testBook));
        mvc.perform(get("/")).andExpect(status().isOk())
                .andExpect(view().name(VIEW_NAME_BOOKS_LIST));
    }

    @Test
    public void viewBookDetails() throws Exception {
        when(dataStorageService.getBookById(1L)).thenReturn(Optional.of(testBook));
        mvc.perform(get(REQUEST_BOOK_DETAILS + "?id=1")).andExpect(status().isOk())
                .andExpect(view().name(VIEW_NAME_BOOK_DETAILS))
                .andExpect(model().attributeExists(MODEL_ATTR_BOOK))
                .andExpect(content().string(containsString(TEST_BOOK_NAME)));
    }
}