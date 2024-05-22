package com.librarymanagementsystem.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class PatronDto implements Serializable {
    private UUID patronId;

    @NotNull(message = "First name should not be null.")
    @Size(max = 100, message = "First name should not exceed 100 characters.")
    private String firstName;

    @NotNull(message = "Last name should not be null.")
    @Size(max = 100, message = "Last name should not exceed 100 characters.")
    private String lastName;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 12 digits.")
    @Size(max=12, message = "Phone number must be exactly 12 digits.")
    private String phoneNumber;

    @NotNull(message = "Email should not be null")
    @Email(message = "Email should be valid.")
    private String email;
}
