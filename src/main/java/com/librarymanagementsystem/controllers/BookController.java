package com.librarymanagementsystem.controllers;

import com.librarymanagementsystem.exceptionhandlers.ValidationException;
import com.librarymanagementsystem.payloads.BookDto;
import com.librarymanagementsystem.services.book.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/create")
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookDto bookDto, BindingResult errors) {
        if (errors.hasErrors()){
            throw new ValidationException(errors);
        }
        return new ResponseEntity<BookDto>(bookService.addBook(bookDto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{bookId}")
    public ResponseEntity<BookDto> updateBook(@NotNull @PathVariable UUID bookId, @Valid @RequestBody BookDto bookDto) {
        return ResponseEntity.ok(bookService.updateBook(bookId, bookDto));
    }

    @GetMapping("/retrieveAll")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/retrieve/{bookId}")
    public ResponseEntity<BookDto> getBookById(@NotNull @PathVariable UUID bookId) {
        return ResponseEntity.ok(bookService.getBookById(bookId));
    }

    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<String> deleteBook(@NotNull @PathVariable UUID bookId) {
        return ResponseEntity.ok(bookService.deleteBook(bookId));
    }
}
