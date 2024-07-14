package com.librarymanagementsystem.controllers;

import com.librarymanagementsystem.payloads.PatronDto;
import com.librarymanagementsystem.services.patron.PatronService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    private final PatronService patronService;

    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    @PostMapping("/create")
    public ResponseEntity<PatronDto> createPatron(@Valid @RequestBody PatronDto patronDto) {
        return new ResponseEntity<PatronDto>(patronService.addPatron(patronDto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{patronId}")
    public ResponseEntity<PatronDto> updateBook(@NotNull @PathVariable UUID patronId,
                                                @Valid @RequestBody PatronDto patronDto) {
        return ResponseEntity.ok(patronService.updatePatron(patronId, patronDto));
    }

    @GetMapping("/retrieveAll")
    public ResponseEntity<List<PatronDto>> getAllBooks() {
        return ResponseEntity.ok(patronService.getAllPatrons());
    }

    @GetMapping("/retrieve/{patronId}")
    public ResponseEntity<PatronDto> getPatronById(@NotNull @PathVariable UUID patronId) {
        return ResponseEntity.ok(patronService.getPatronById(patronId));
    }

    @DeleteMapping("/delete/{patronId}")
    public ResponseEntity<String> deleteBook(@NotNull @PathVariable UUID patronId) {
        return ResponseEntity.ok(patronService.deletePatron(patronId));
    }
}
