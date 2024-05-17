package com.librarymanagementsystem.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "borrowing_record")
@Getter
@Setter
@RequiredArgsConstructor
public class BorrowingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "borrowing_id")
    private UUID borrowingId;

    private LocalDate borrowingDate;

    private LocalDate returnDate;

    private BorrowStatus borrowStatus;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "patron_id")
    private Patron patron;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;
}
