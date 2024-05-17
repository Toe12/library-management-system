package com.librarymanagementsystem.integrationtests.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.librarymanagementsystem.integrationtests.TestUtils;
import com.librarymanagementsystem.entities.BorrowStatus;
import com.librarymanagementsystem.payloads.BorrowingRecordDto;
import com.librarymanagementsystem.services.borrowingrecord.BorrowingRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BorrowBooksControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowingRecordService borrowingRecordService;

    @Autowired
    private ObjectMapper objectMapper;

    private BorrowingRecordDto borrowingRecordDto;

    @BeforeEach
    public void setUp() {
        borrowingRecordDto = TestUtils.borrowingRecordDto();
    }

    @Test
    void recordBorrowedBook() throws Exception {
        UUID bookId = borrowingRecordDto.getBook().getBookId();
        UUID patronId = borrowingRecordDto.getPatron().getPatronId();

        when(borrowingRecordService.addBorrowingRecord(patronId, bookId)).thenReturn(borrowingRecordDto);

        String response = mockMvc.perform(post("/api/borrow/" + bookId +"/patron/" + patronId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        BorrowingRecordDto result = objectMapper.readValue(response, BorrowingRecordDto.class);

        assertEquals(borrowingRecordDto.getBorrowingId(), result.getBorrowingId());
        verify(borrowingRecordService, times(1)).addBorrowingRecord(patronId, bookId);
    }

    @Test
    void returnBorrowedBook() throws Exception {
        UUID bookId = borrowingRecordDto.getBook().getBookId();
        UUID patronId = borrowingRecordDto.getPatron().getPatronId();

        borrowingRecordDto.setReturnDate(LocalDate.now());
        borrowingRecordDto.setBorrowStatus(BorrowStatus.RETURNED);

        when(borrowingRecordService.updateBorrowRecord(patronId, bookId)).thenReturn(borrowingRecordDto);

        String response = mockMvc.perform(put("/api/return/" + bookId +"/patron/" + patronId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        BorrowingRecordDto result = objectMapper.readValue(response, BorrowingRecordDto.class);

        assertEquals(borrowingRecordDto.getBorrowingId(), result.getBorrowingId());
        verify(borrowingRecordService, times(1)).updateBorrowRecord(patronId, bookId);
    }


}
