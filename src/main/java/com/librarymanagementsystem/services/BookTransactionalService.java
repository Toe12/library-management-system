package com.librarymanagementsystem.services;

import com.librarymanagementsystem.models.Book;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class BookTransactionalService {

    private final BookService bookService;

    public BookTransactionalService(BookService bookService) {
        this.bookService = bookService;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public Book findBookById(UUID bookId) {
        return bookService.findBookById(bookId);
    }
}