package com.librarymanagementsystem.daos;

import com.librarymanagementsystem.entities.Patron;
import com.librarymanagementsystem.repositories.PatronRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class PatronDao {
    private final PatronRepository patronRepository;

    public PatronDao(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    public Patron save(Patron patron) {
        return patronRepository.save(patron);
    }

    public Optional<Patron> findPatronById(UUID patronId) {
        return patronRepository.findById(patronId);
    }

    public List<Patron> findAllPatrons() {
        return patronRepository.findAll();
    }

    public Optional<Patron> findByPatronId(UUID patronId) {
        return patronRepository.findById(patronId);
    }

    public void deletePatronById(UUID patronId) {
        patronRepository.deleteById(patronId);
    }
}
