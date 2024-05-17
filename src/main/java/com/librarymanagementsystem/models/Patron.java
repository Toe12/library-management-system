package com.librarymanagementsystem.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "patron")
public class Patron {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "patron_id")
    private UUID patronId;

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "phone", length = 12)
    private String phoneNumber;

    @Column(name = "email", length = 100)
    private String email;

    @OneToMany(mappedBy = "patron", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<BorrowingRecord> borrowingRecords;
}
