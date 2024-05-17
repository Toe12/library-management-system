package com.librarymanagementsystem.dao;

import com.librarymanagementsystem.exceptionhandlers.borrowrecord.BorrowingRecordException;
import com.librarymanagementsystem.models.BorrowStatus;
import com.librarymanagementsystem.models.BorrowingRecord;
import com.librarymanagementsystem.repositories.BorrowingRecordRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class BorrowingRecordDao {
    private final BorrowingRecordRepository borrowingRecordRepository;

    public BorrowingRecordDao(BorrowingRecordRepository borrowingRecordRepository) {
        this.borrowingRecordRepository = borrowingRecordRepository;
    }

    public BorrowingRecord save(BorrowingRecord borrowingRecord) {
        return borrowingRecordRepository.save(borrowingRecord);
    }

    public BorrowingRecord findById(UUID id) {
        return borrowingRecordRepository.findById(id).
                orElseThrow(() -> new BorrowingRecordException("Borrowing record not found"));
    }

    public Optional<BorrowingRecord> findBorrowStatus(UUID bookId, BorrowStatus borrowStatus) {
        return borrowingRecordRepository.findByBookBookIdAndBorrowStatus(bookId, borrowStatus);
    }
}
