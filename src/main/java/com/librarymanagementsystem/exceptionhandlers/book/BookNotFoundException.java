package com.librarymanagementsystem.exceptionhandlers.book;

public class BookNotFoundException extends BookException {
    public BookNotFoundException(String message) {
        super(message);
    }
}
