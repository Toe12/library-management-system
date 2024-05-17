package com.librarymanagementsystem.integrationtests;

import com.librarymanagementsystem.payloads.BookDto;
import com.librarymanagementsystem.payloads.BorrowingRecordDto;
import com.librarymanagementsystem.payloads.PatronDto;

import java.time.LocalDate;
import java.util.UUID;

public class TestUtils {

    public static BookDto bookDto() {
        BookDto bookDTO = new BookDto();
        bookDTO.setBookId(UUID.randomUUID());
        bookDTO.setTitle("Book Title");
        bookDTO.setIsbn("978-1-6112-2991-245");
        bookDTO.setAuthorLastName("John");
        bookDTO.setAuthorFirstName("Doe");
        return bookDTO;
    }

    public static PatronDto patronDto() {
        PatronDto patronDTO = new PatronDto();
        patronDTO.setPatronId(UUID.randomUUID());
        patronDTO.setFirstName("John");
        patronDTO.setLastName("Doe");
        patronDTO.setEmail("patron@email.com");
        patronDTO.setPhoneNumber("1234567890");
        return patronDTO;
    }

    public static BorrowingRecordDto borrowingRecordDto() {
        BorrowingRecordDto borrowingRecordDTO = new BorrowingRecordDto();
        borrowingRecordDTO.setBorrowingId(UUID.randomUUID());
        borrowingRecordDTO.setBorrowingDate(LocalDate.now());
        borrowingRecordDTO.setPatron(patronDto());
        borrowingRecordDTO.setBook(bookDto());
        return borrowingRecordDTO;
    }
}
