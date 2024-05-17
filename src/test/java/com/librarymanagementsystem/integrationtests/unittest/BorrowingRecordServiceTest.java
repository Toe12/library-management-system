package com.librarymanagementsystem.integrationtests.unittest;

import com.librarymanagementsystem.exceptionhandlers.borrowrecord.BorrowingRecordAlreadyExistsException;
import com.librarymanagementsystem.integrationtests.TestUtils;
import com.librarymanagementsystem.entities.BorrowStatus;
import com.librarymanagementsystem.payloads.BorrowingRecordDto;
import com.librarymanagementsystem.services.borrowingrecord.BorrowingRecordService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BorrowingRecordServiceTest {
    private AutoCloseable closeable;

    @Mock
    private BorrowingRecordService borrowingRecordService;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void addBorrowingRecord() {
        BorrowingRecordDto expectedBorrowingRecordDto = TestUtils.borrowingRecordDto();

        expectedBorrowingRecordDto.setBorrowStatus(BorrowStatus.BORROWED);
        expectedBorrowingRecordDto.setBorrowingDate(LocalDate.now());

        UUID patronId = expectedBorrowingRecordDto.getPatron().getPatronId();
        UUID borrowId = expectedBorrowingRecordDto.getBook().getBookId();

        when(borrowingRecordService.addBorrowingRecord(patronId, borrowId)).thenReturn(expectedBorrowingRecordDto);

        BorrowingRecordDto actualBorrowingRecordDto = borrowingRecordService.addBorrowingRecord(patronId, borrowId);

        assertNotNull(actualBorrowingRecordDto, "The returned BorrowingRecordDto should not be null.");
        assertEquals(expectedBorrowingRecordDto, actualBorrowingRecordDto,
                "The returned BorrowingRecordDto should match the expected BorrowingRecordDto.");

        verify(borrowingRecordService, times(1)).addBorrowingRecord(patronId, borrowId);
    }

    @Test
    void updateBorrowingRecord() {
        BorrowingRecordDto expectedBorrowingRecordDto = TestUtils.borrowingRecordDto();

        expectedBorrowingRecordDto.setBorrowStatus(BorrowStatus.RETURNED);
        expectedBorrowingRecordDto.setReturnDate(LocalDate.now());

        UUID patronId = expectedBorrowingRecordDto.getPatron().getPatronId();
        UUID borrowId = expectedBorrowingRecordDto.getBook().getBookId();

        when(borrowingRecordService.updateBorrowRecord(patronId, borrowId)).thenReturn(expectedBorrowingRecordDto);

        BorrowingRecordDto actualBorrowingRecordDto = borrowingRecordService.updateBorrowRecord(patronId, borrowId);

        assertNotNull(actualBorrowingRecordDto, "The returned BorrowingRecordDto should not be null.");
        assertEquals(expectedBorrowingRecordDto, actualBorrowingRecordDto,
                "The returned BorrowingRecordDto should match the expected BorrowingRecordDto.");

        verify(borrowingRecordService, times(1)).updateBorrowRecord(patronId, borrowId);
    }

    @Test
    void throw_BorrowingRecordAlreadyExistsException_when_book_is_borrowed_by_another_patron() {
        BorrowingRecordDto borrowingRecordDto = TestUtils.borrowingRecordDto();

        borrowingRecordDto.setBorrowStatus(BorrowStatus.BORROWED);
        borrowingRecordDto.setReturnDate(LocalDate.now());

        // assume this is another patron in the db
        UUID another_patronId = UUID.randomUUID();
        UUID bookId = borrowingRecordDto.getBook().getBookId();

        doThrow(new BorrowingRecordAlreadyExistsException("A borrowing record indicates that the book has been borrowed by another patron."))
                .when(borrowingRecordService).addBorrowingRecord(another_patronId, bookId);

        assertThrows(BorrowingRecordAlreadyExistsException.class,
                () -> borrowingRecordService.addBorrowingRecord(another_patronId, bookId));

        verify(borrowingRecordService, times(1)).addBorrowingRecord(another_patronId, bookId);
    }

    @Test
    void throw_BorrowingRecordAlreadyExistsException_when_book_is_already_returned_and_unable_to_return_borrowing_record() {
        BorrowingRecordDto borrowingRecordDto = TestUtils.borrowingRecordDto();

        // assume that the book is already returned
        borrowingRecordDto.setBorrowStatus(BorrowStatus.RETURNED);
        borrowingRecordDto.setReturnDate(LocalDate.now());

        // assume this is another patron in the db
        UUID another_patronId = UUID.randomUUID();
        UUID bookId = borrowingRecordDto.getBook().getBookId();

        doThrow(new BorrowingRecordAlreadyExistsException("A borrowing record indicates that the book has been returned."))
                .when(borrowingRecordService).addBorrowingRecord(another_patronId, bookId);

        assertThrows(BorrowingRecordAlreadyExistsException.class,
                () -> borrowingRecordService.addBorrowingRecord(another_patronId, bookId));

        verify(borrowingRecordService, times(1)).addBorrowingRecord(another_patronId, bookId);
    }
}
