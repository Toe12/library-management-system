package com.librarymanagementsystem.services.patron;

import com.librarymanagementsystem.daos.PatronDao;
import com.librarymanagementsystem.exceptionhandlers.book.BookNotFoundException;
import com.librarymanagementsystem.entities.Patron;
import com.librarymanagementsystem.payloads.PatronDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PatronService {
    private final PatronDao patronDao;

    private final ModelMapper modelMapper;

    public PatronService(PatronDao patronDao, ModelMapper modelMapper) {
        this.patronDao = patronDao;
        this.modelMapper = modelMapper;
    }

    @CacheEvict(cacheNames = "getAllPatrons", key = "'allPatrons'")
    public PatronDto addPatron(PatronDto patronDto) {
        Patron patron = modelMapper.map(patronDto, Patron.class);
        Patron savedPatron = patronDao.save(patron);
        return modelMapper.map(savedPatron, PatronDto.class);
    }

    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "patron", key = "#patronId"),
                    @CacheEvict(cacheNames = "getAllPatrons", key = "'allPatrons'")
            },
            put = {
                    @CachePut(cacheNames = "patron", key = "#patronId")
            }
    )
    public PatronDto updatePatron(UUID patronId, PatronDto patronDto) {
        log.info("Updating patron with id {}", patronId);
        Patron patronToUpdate = findPatronById(patronId);
        patronDto.setPatronId(patronToUpdate.getPatronId());

        modelMapper.map(patronDto, patronToUpdate);

        Patron updatedPatron = patronDao.save(patronToUpdate);

        log.info("Updated patron with id {}", patronId);
        return modelMapper.map(updatedPatron, PatronDto.class);
    }

    public Patron findPatronById(UUID patronId) {
        return patronDao.findByPatronId(patronId)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));
    }

    @Cacheable(cacheNames = "patron", key = "#patronId")
    public PatronDto getPatronById(UUID patronId) {
        log.info("Fetching patron with id {}", patronId);
        Patron patron = findPatronById(patronId);
        return modelMapper.map(patron, PatronDto.class);
    }

    @Cacheable(cacheNames = "getAllPatrons", key = "'allPatrons'")
    public List<PatronDto> getAllPatrons() {
        List<Patron> patrons = patronDao.findAllPatrons();
        return patrons
                .stream()
                .map(patron -> modelMapper.map(patron, PatronDto.class))
                .toList();
    }

    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "patron", key = "#patronId"),
                    @CacheEvict(cacheNames = "getAllPatrons", key = "'allPatrons'")
            },
            put = {
                    @CachePut(cacheNames = "patron", key = "#patronId")
            }
    )
    public String deletePatron(UUID patronId) {
        Patron patron = findPatronById(patronId);
        patronDao.deletePatronById(patronId);
        return "Deleted " + patron.getFirstName() + " " + patron.getLastName();
    }
}
