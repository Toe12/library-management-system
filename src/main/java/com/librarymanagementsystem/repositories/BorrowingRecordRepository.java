package com.librarymanagementsystem.repositories;

import com.librarymanagementsystem.entities.BorrowStatus;
import com.librarymanagementsystem.entities.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, UUID> {
    Optional<BorrowingRecord> findByBookBookIdAndBorrowStatus(UUID bookId, BorrowStatus status);
}
