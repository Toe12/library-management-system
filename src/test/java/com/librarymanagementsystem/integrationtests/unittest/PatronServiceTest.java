package com.librarymanagementsystem.integrationtests.unittest;

import com.librarymanagementsystem.exceptionhandlers.patron.PatronNotFoundException;
import com.librarymanagementsystem.integrationtests.TestUtils;
import com.librarymanagementsystem.payloads.PatronDto;
import com.librarymanagementsystem.services.patron.PatronService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatronServiceTest {
    private AutoCloseable closeable;

    @Mock
    private PatronService patronService;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void createPatron() {
        PatronDto expectedPatronDto = TestUtils.patronDto();

        when(patronService.addPatron(isA(PatronDto.class))).thenReturn(expectedPatronDto);

        PatronDto actualPatronDto = patronService.addPatron(expectedPatronDto);

        assertNotNull(actualPatronDto, "The returned PatronDto should not be null");
        assertEquals(expectedPatronDto, actualPatronDto, "The returned BookDto should match the expected BookDto");

        verify(patronService, times(1)).addPatron(isA(PatronDto.class));
    }

    @Test
    void updatePatron() {
        PatronDto expectedPatronDto = TestUtils.patronDto();

        UUID patronId = expectedPatronDto.getPatronId();

        // update email
        expectedPatronDto.setEmail("patron@domain.com");

        when(patronService.updatePatron(isA(UUID.class), isA(PatronDto.class))).thenReturn(expectedPatronDto);

        PatronDto actualPatronDto = patronService.updatePatron(patronId, expectedPatronDto);

        assertNotNull(actualPatronDto, "The returned PatronDto should not be null");
        assertEquals(expectedPatronDto, actualPatronDto,
                "The returned PatronDto should match the expected PatronDto");

        verify(patronService, times(1)).updatePatron(isA(UUID.class), isA(PatronDto.class));
    }

    @Test
    void retrievePatronByPatronId() {
        PatronDto expectedPatronDto = TestUtils.patronDto();

        UUID patronId = expectedPatronDto.getPatronId();

        when(patronService.getPatronById(patronId)).thenReturn(expectedPatronDto);

        PatronDto actualPatronDto = patronService.getPatronById(patronId);

        assertNotNull(actualPatronDto, "The returned PatronDto should not be null");
        assertEquals(expectedPatronDto, actualPatronDto, "The returned BookDto should match the expected BookDto");

        verify(patronService, times(1)).getPatronById(patronId);
    }

    @Test
    void throw_notFoundException_whenPatronNotFound() {
        UUID fake_bookId = UUID.randomUUID(); // fake book id

        doThrow(new PatronNotFoundException("Patron not found."))
                .when(patronService).findPatronById(fake_bookId);

        assertThrows(PatronNotFoundException.class,
                () -> patronService.findPatronById(fake_bookId));
    }

    @Test
    void retrieveAllPatrons() {
        List<PatronDto> expectedPatronDtoList = List.of(TestUtils.patronDto());

        when(patronService.getAllPatrons()).thenReturn(expectedPatronDtoList);

        List<PatronDto> actualPatronDtoList = patronService.getAllPatrons();

        assertFalse(actualPatronDtoList.isEmpty(), "The returned BookDto list should not be empty");
        assertEquals(expectedPatronDtoList, actualPatronDtoList,
                "The returned BookDto list should match the expected BookDto");

        verify(patronService, times(1)).getAllPatrons();
    }

    @Test
    void deleteBook() {
        PatronDto patronDto = TestUtils.patronDto();

        UUID patronId = patronDto.getPatronId();

        String name = patronDto.getFirstName() + " " + patronDto.getLastName();

        String expectedResult = "Deleted " + name;

        when(patronService.deletePatron(patronId)).thenReturn(expectedResult);

        String actualResult = patronService.deletePatron(patronId);

        assertNotNull(actualResult, "The actualResult should not be null");
        assertEquals(expectedResult, actualResult, "The actualResult should match the expected Result");

        verify(patronService, times(1)).deletePatron(patronId);
    }
}
