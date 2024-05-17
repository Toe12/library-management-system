package com.librarymanagementsystem.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.librarymanagementsystem.models.BorrowStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class BorrowingRecordDto{
    private UUID borrowingId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate borrowingDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate returnDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BorrowStatus borrowStatus;

    private BookDto book;

    private PatronDto patron;
}
