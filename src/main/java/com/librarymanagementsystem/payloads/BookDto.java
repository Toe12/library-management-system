package com.librarymanagementsystem.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class BookDto implements Serializable {
    private UUID bookId;

    @NotNull(message = "Title should not be null.")
    @Size(max = 100, message = "Title should not exceed 100 characters.")
    private String title;

    @NotNull(message = "Aurthor First name should not be null.")
    @Size(max = 100, message = "Author last name should not exceed 100 characters.")
    private String authorFirstName;

    @NotNull(message = "Author Last name should not be null.")
    @Size(max = 100, message = "Author last name should not exceed 100 characters.")
    private String authorLastName;

    @NotNull(message = "ISBN should not be null. " +
            "Suggestion: ISBN should be 10 or 13 digits long, optionally starting with '978' or '979', " +
            "and may contain hyphens.")
    private String isbn;
}
