package com.librarymanagementsystem.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "book_id")
    private UUID bookId;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name="author_first_name", length = 100)
    private String authorFirstName;

    @Column(name="author_last_name", length = 100)
    private String authorLastName;

    @Column(name = "publicationYear")
    private int publicationYear;

    @Column(name = "isbn", unique = true, length = 100)
    private String isbn;

    @OneToMany(mappedBy="book", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<BorrowingRecord> borrowingRecords;
}
