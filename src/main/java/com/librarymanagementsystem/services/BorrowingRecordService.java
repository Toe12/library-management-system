package com.librarymanagementsystem.services;

import com.librarymanagementsystem.dao.BorrowingRecordDao;
import com.librarymanagementsystem.exceptionhandlers.borrowrecord.BorrowingRecordAlreadyExistsException;
import com.librarymanagementsystem.models.Book;
import com.librarymanagementsystem.models.BorrowStatus;
import com.librarymanagementsystem.models.BorrowingRecord;
import com.librarymanagementsystem.models.Patron;
import com.librarymanagementsystem.payload.BorrowingRecordDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class BorrowingRecordService {
    private final BorrowingRecordDao borrowRecordDao;

    private final BookTransactionalService bookService;

    private final PatronTransactionalService patronService;

    private final ModelMapper modelMapper;

    public BorrowingRecordService(BorrowingRecordDao borrowRecordDao, BookTransactionalService bookService, PatronTransactionalService patronService, ModelMapper modelMapper) {
        this.borrowRecordDao = borrowRecordDao;
        this.bookService = bookService;
        this.patronService = patronService;
        this.modelMapper = modelMapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public BorrowingRecordDto addBorrowingRecord(UUID patronId, UUID bookId) {
        //retrieve book
        Book retrievedBook = bookService.findBookById(bookId);

        //retrieve patron
        Patron retrievedPatron = patronService.findByPatronId(patronId);

        // check if the book is borrowed
        Optional<BorrowingRecord> borrowingRecordToUpdate = borrowRecordDao.findBorrowStatus(bookId, BorrowStatus.BORROWED);

        if (borrowingRecordToUpdate.isPresent()) {
            throw new BorrowingRecordAlreadyExistsException("A borrowing record indicates that the book has been borrowed by another patron.");
        }

        BorrowingRecord borrowingRecordToSave = new BorrowingRecord();
        borrowingRecordToSave.setBook(retrievedBook);
        borrowingRecordToSave.setPatron(retrievedPatron);
        borrowingRecordToSave.setBorrowStatus(BorrowStatus.BORROWED);

        borrowingRecordToSave.setBorrowingDate(LocalDate.now());

        BorrowingRecord savedBorrowingRecord = borrowRecordDao.save(borrowingRecordToSave);

        log.info("Borrowing record saved: {}", savedBorrowingRecord.getBorrowingId());

        return modelMapper.map(savedBorrowingRecord, BorrowingRecordDto.class);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public BorrowingRecordDto updateBorrowRecord(UUID patronId, UUID bookId) {
        //retrieve book
        Book retrievedBook = bookService.findBookById(bookId);

        //retrieve patron
        Patron retrievedPatron = patronService.findByPatronId(patronId);

        BorrowingRecord borrowingRecordToUpdate = borrowRecordDao.findBorrowStatus(bookId, BorrowStatus.BORROWED)
                .orElseThrow(() -> new BorrowingRecordAlreadyExistsException("\"A borrowing record indicates that the book has been returned."));

        borrowingRecordToUpdate.setBorrowingDate(borrowingRecordToUpdate.getBorrowingDate());

        borrowingRecordToUpdate.setBook(retrievedBook);
        borrowingRecordToUpdate.setPatron(retrievedPatron);
        borrowingRecordToUpdate.setBorrowStatus(BorrowStatus.RETURNED);
        borrowingRecordToUpdate.setReturnDate(LocalDate.now());

        BorrowingRecord savedBorrowingRecord = borrowRecordDao.save(borrowingRecordToUpdate);

        log.info("Returned borrowing record: {}", savedBorrowingRecord.getBorrowingId());

        return modelMapper.map(savedBorrowingRecord, BorrowingRecordDto.class);
    }

}
