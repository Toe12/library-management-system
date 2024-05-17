package com.librarymanagementsystem.services.patron;

import com.librarymanagementsystem.entities.Patron;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PatronTransactionalService {
    private final PatronService patronService;

    public PatronTransactionalService(PatronService patronService) {
        this.patronService = patronService;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public Patron findByPatronId(UUID patronId){
        return this.patronService.findPatronById(patronId);
    }
}
