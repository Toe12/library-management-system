package com.librarymanagementsystem.daos;

import com.librarymanagementsystem.entities.Book;
import com.librarymanagementsystem.repositories.BookRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class BookDao {
    private final BookRepository bookRepository;

    public BookDao(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> retrieveAllBooks(){
        return bookRepository.findAll();
    }

    public Optional<Book> findBookById(UUID bookId) {
        return bookRepository.findById(bookId);
    }

    public void deleteBookById(UUID bookId) {
        bookRepository.deleteById(bookId);
    }

}
