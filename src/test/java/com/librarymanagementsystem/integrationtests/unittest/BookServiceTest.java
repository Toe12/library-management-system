package com.librarymanagementsystem.integrationtests.unittest;

import com.librarymanagementsystem.exceptionhandlers.book.BookNotFoundException;
import com.librarymanagementsystem.integrationtests.TestUtils;
import com.librarymanagementsystem.payloads.BookDto;
import com.librarymanagementsystem.services.book.BookService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {
    private AutoCloseable closeable;

    @Mock
    private BookService bookService;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void createBook() {
        BookDto expectedBookDto = TestUtils.bookDto();

        when(bookService.addBook(isA(BookDto.class))).thenReturn(expectedBookDto);

        BookDto actualBookDto = bookService.addBook(expectedBookDto);

        assertNotNull(actualBookDto, "The returned BookDto should not be null");
        assertEquals(expectedBookDto, actualBookDto, "The returned BookDto should match the expected BookDto");

        verify(bookService, times(1)).addBook(isA(BookDto.class));
    }

    @Test
    void updateBook() {
        BookDto expectedBookDto = TestUtils.bookDto();
        UUID bookId = expectedBookDto.getBookId();
        // update ISBN
        expectedBookDto.setIsbn("978-1-6112-2993-2");

        when(bookService.updateBook(isA(UUID.class), isA(BookDto.class))).thenReturn(expectedBookDto);

        BookDto actualBookDto = bookService.updateBook(bookId, expectedBookDto);

        assertNotNull(actualBookDto, "The returned BookDto should not be null");
        assertEquals(expectedBookDto, actualBookDto, "The returned BookDto should match the expected BookDto");

        verify(bookService, times(1)).updateBook(isA(UUID.class), isA(BookDto.class));
    }

    @Test
    void retrieveBookByBookId() {
        UUID bookId = TestUtils.bookDto().getBookId();

        BookDto expectedBookDto = TestUtils.bookDto();

        when(bookService.getBookById(bookId)).thenReturn(expectedBookDto);

        BookDto actualBookDto = bookService.getBookById(bookId);

        assertNotNull(actualBookDto, "The returned BookDto should not be null");
        assertEquals(expectedBookDto, actualBookDto, "The returned BookDto should match the expected BookDto");

        verify(bookService, times(1)).getBookById(bookId);
    }

    @Test
    void throw_notFoundException_whenBookNotFound() {
        UUID fake_bookId = UUID.randomUUID(); // fake book id

        doThrow(new BookNotFoundException("Book not found."))
                .when(bookService).getBookById(fake_bookId);

        assertThrows(BookNotFoundException.class,
                () -> bookService.getBookById(fake_bookId));

        verify(bookService, times(1)).getBookById(fake_bookId);
    }

    @Test
    void retrieveAllBooks() {
        List<BookDto> expectedBookDtoList = List.of(TestUtils.bookDto());

        when(bookService.getAllBooks()).thenReturn(expectedBookDtoList);

        List<BookDto> actualBookDtoList = bookService.getAllBooks();

        assertFalse(actualBookDtoList.isEmpty(), "The returned BookDto list should not be empty");
        assertEquals(expectedBookDtoList, actualBookDtoList,
                "The returned BookDto list should match the expected BookDto");

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void deleteBook() {
        BookDto bookDto = TestUtils.bookDto();

        UUID bookId = bookDto.getBookId();

        String title = bookDto.getTitle();

        String expectedResult = "Deleted " + title;

        when(bookService.deleteBook(bookId)).thenReturn(expectedResult);

        String actualResult = bookService.deleteBook(bookId);

        assertNotNull(actualResult, "The actualResult should not be null");
        assertEquals(expectedResult, actualResult, "The actualResult should match the expected Result");

        verify(bookService, times(1)).deleteBook(bookId);
    }

}
