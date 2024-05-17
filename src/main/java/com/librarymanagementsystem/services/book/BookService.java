package com.librarymanagementsystem.services.book;

import com.librarymanagementsystem.daos.BookDao;
import com.librarymanagementsystem.exceptionhandlers.book.BookException;
import com.librarymanagementsystem.exceptionhandlers.book.BookNotFoundException;
import com.librarymanagementsystem.entities.Book;
import com.librarymanagementsystem.payloads.BookDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class BookService {

    private final BookDao bookDao;
    private final ModelMapper modelMapper;

    public BookService(BookDao bookDao, ModelMapper modelMapper) {
        this.bookDao = bookDao;
        this.modelMapper = modelMapper;
    }

    public BookDto addBook(BookDto bookDto) {
        try {
            Book book = modelMapper.map(bookDto, Book.class);
            Book savedBook = bookDao.save(book);
            return modelMapper.map(savedBook, BookDto.class);
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage());
            throw new BookException("ISBN already exists");
        }
    }

    @CachePut(cacheNames = "book", key = "#bookId")
    public BookDto updateBook(UUID bookId, BookDto bookDto) {
        try {
            log.info("Updating book with id {} from db", bookId);
            Book bookToUpdate = findBookById(bookId);
            bookDto.setBookId(bookToUpdate.getBookId());
            modelMapper.map(bookDto, bookToUpdate); // Corrected mapping direction
            Book updatedBook = bookDao.save(bookToUpdate);
            log.info("Updated book with id {} from db", bookId);
            return modelMapper.map(updatedBook, BookDto.class);
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage());
            throw new BookException("ISBN already exists");
        }
    }

    public Book findBookById(UUID bookId) {
        return bookDao.findBookById(bookId).orElseThrow(() -> new BookNotFoundException("Book not found"));
    }

    @Cacheable(cacheNames = "book", key = "#bookId")
    public BookDto getBookById(UUID bookId) {
        log.info("Fetching book with id {} from db", bookId);
        Book book = findBookById(bookId);
        return modelMapper.map(book, BookDto.class);
    }

    public List<BookDto> getAllBooks() {
        List<Book> books = bookDao.retrieveAllBooks();
        return books.stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .toList();
    }

    @CacheEvict(cacheNames = "book", key = "#bookId")
    public String deleteBook(UUID bookId) {
        Book book = findBookById(bookId);
        bookDao.deleteBookById(bookId);
        return "Deleted " + book.getTitle();
    }
}