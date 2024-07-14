package com.librarymanagementsystem.integrationtests.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.librarymanagementsystem.integrationtests.TestUtils;
import com.librarymanagementsystem.payloads.PatronDto;
import com.librarymanagementsystem.services.patron.PatronService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PatronControllerTest extends AbstractIntegrationTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatronService patronService;

    @Autowired
    private ObjectMapper objectMapper;

    private PatronDto patronDto;

    @BeforeEach
    void setUp() {
        patronDto = TestUtils.patronDto();
    }

    @Test
    void testCreatePatron() throws Exception {
        when(patronService.addPatron(any(PatronDto.class))).thenReturn(patronDto);

        String response = mockMvc.perform(post("/api/patrons/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patronDto)))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PatronDto result = objectMapper.readValue(response, PatronDto.class);

        assertEquals(patronDto.getPatronId(), result.getPatronId());

        verify(patronService, times(1)).addPatron(isA(PatronDto.class));
    }

    @Test
    void testUpdatePatron() throws Exception {
        UUID patronId = UUID.randomUUID();
        when(patronService.updatePatron(isA(UUID.class), isA(PatronDto.class))).thenReturn(patronDto);

        String response = mockMvc.perform(put("/api/patrons/update/{patronId}", patronId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patronDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PatronDto result = objectMapper.readValue(response, PatronDto.class);

        assertEquals(patronDto.getPatronId(), result.getPatronId());

        verify(patronService, times(1)).updatePatron(isA(UUID.class), isA(PatronDto.class));
    }

    @Test
    void testGetAllPatrons() throws Exception {
        List<PatronDto> patronList = List.of(patronDto);
        when(patronService.getAllPatrons()).thenReturn(patronList);

        String response = mockMvc.perform(get("/api/patrons/retrieveAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<PatronDto> patrons = objectMapper.readValue(response,
                objectMapper.getTypeFactory().constructCollectionType(List.class, PatronDto.class));

        assertFalse(patrons.isEmpty());

        verify(patronService, times(1)).getAllPatrons();
    }

    @Test
    void testGetPatronById() throws Exception {
        UUID patronId = UUID.randomUUID();
        when(patronService.getPatronById(patronId)).thenReturn(patronDto);

        String response = mockMvc.perform(get("/api/patrons/retrieve/{patronId}", patronId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PatronDto result = objectMapper.readValue(response, PatronDto.class);

        assertEquals(patronDto.getPatronId(), result.getPatronId());
        verify(patronService, times(1)).getPatronById(patronId);
    }

    @Test
    void testDeletePatron() throws Exception {
        UUID patronId = UUID.randomUUID();
        String expectedResult = "Patron deleted successfully";
        when(patronService.deletePatron(patronId)).thenReturn(expectedResult);

        String actualResult = mockMvc.perform(delete("/api/patrons/delete/{patronId}", patronId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(expectedResult, actualResult);

        verify(patronService, times(1)).deletePatron(patronId);
    }
}
