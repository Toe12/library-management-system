package com.librarymanagementsystem.integrationtests.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.librarymanagementsystem.integrationtests.TestUtils;
import com.librarymanagementsystem.payload.BookDto;
import com.librarymanagementsystem.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private BookDto bookDto;

    @BeforeEach
    public void setup() {
        bookDto = TestUtils.bookDto();
    }

    @Test
    void testCreateBook() throws Exception {
        when(bookService.addBook(isA(BookDto.class))).thenReturn(bookDto);

        String response = mockMvc.perform(post("/api/books/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        BookDto result = objectMapper.readValue(response, BookDto.class);

        assertEquals(bookDto.getBookId(), result.getBookId());
        verify(bookService, times(1)).addBook(isA(BookDto.class));
    }

    @Test
    void testUpdateBook() throws Exception {
        UUID bookId = bookDto.getBookId();
        when(bookService.updateBook(isA(UUID.class), isA(BookDto.class))).thenReturn(bookDto);

        String response = mockMvc.perform(put("/api/books/update/{bookId}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        BookDto result = objectMapper.readValue(response, BookDto.class);

        assertEquals(bookDto.getBookId(), result.getBookId());
        verify(bookService, times(1)).updateBook(isA(UUID.class), isA(BookDto.class));
    }

    @Test
    void testGetBookById() throws Exception {
        UUID bookId = bookDto.getBookId();
        when(bookService.getBookById(bookId)).thenReturn(bookDto);

        String response = mockMvc.perform(get("/api/books/retrieve/{bookId}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        BookDto result = objectMapper.readValue(response, BookDto.class);

        assertEquals(bookDto.getBookId(), result.getBookId());
        verify(bookService, times(1)).getBookById(bookId);
    }

    @Test
    void testGetAllBooks() throws Exception {
        List<BookDto> bookList = List.of(bookDto);
        when(bookService.getAllBooks()).thenReturn(bookList);

        String response = mockMvc.perform(get("/api/books/retrieveAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<BookDto> bookDtos = objectMapper.readValue(response,
                objectMapper.getTypeFactory().constructCollectionType(List.class, BookDto.class));

        assertFalse(bookDtos.isEmpty());

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void testDeleteBook() throws Exception {
        UUID bookId = UUID.randomUUID();
        String expectedResult = "Book deleted successfully";

        when(bookService.deleteBook(bookId)).thenReturn(expectedResult);

        String actualResult = mockMvc.perform(delete("/api/books/delete/{bookId}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(expectedResult, actualResult);
        verify(bookService, times(1)).deleteBook(bookId);
    }
}
