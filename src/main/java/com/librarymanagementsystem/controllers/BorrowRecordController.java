package com.librarymanagementsystem.controllers;

import com.librarymanagementsystem.payloads.BorrowingRecordDto;
import com.librarymanagementsystem.services.borrowingrecord.BorrowingRecordService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class BorrowRecordController {

    private final BorrowingRecordService borrowingRecordService;

    public BorrowRecordController(BorrowingRecordService borrowingRecordService) {
        this.borrowingRecordService = borrowingRecordService;
    }

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecordDto> borrowBook(@NotNull @PathVariable UUID bookId,
                                                         @NotNull @PathVariable UUID patronId) {
        return ResponseEntity.ok().body(borrowingRecordService.
                addBorrowingRecord(patronId, bookId));
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecordDto> updateBorrowStatus(@NotNull @PathVariable UUID bookId,
                                                         @NotNull @PathVariable UUID patronId) {
        return ResponseEntity.ok().body(borrowingRecordService
                .updateBorrowRecord(patronId, bookId));
    }
}
