package com.fetch.rewards.receiptprocessor.controller;

import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fetch.rewards.receiptprocessor.entities.Purchase;
import com.fetch.rewards.receiptprocessor.service.CalculateServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

class ReceiptControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CalculateServiceImpl calculateServiceImpl;

    @InjectMocks
    private ReceiptController receiptController;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(receiptController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }



    @Test
    void testProcessReceipt() throws Exception {
        String purchaseJson = new String(Files.readAllBytes(Paths.get("src/test/resources/data/TestData1.json")));;

        when(calculateServiceImpl.processReceipt(any(Purchase.class)))
                .thenReturn(UUID.randomUUID().toString());

        mockMvc.perform(MockMvcRequestBuilders.post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(purchaseJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void testGetPointsSuccess() throws Exception {
        // Read and parse the test data
        String purchaseJson = new String(Files.readAllBytes(Paths.get("src/test/resources/data/TestData1.json")));

        Purchase purchase = objectMapper.readValue(purchaseJson, Purchase.class);
        // Mock the service to return a fixed ID
        String mockId = UUID.randomUUID().toString();
        when(calculateServiceImpl.processReceipt(any(Purchase.class))).thenReturn(mockId);
        when(calculateServiceImpl.getPurchaseById(any(String.class))).thenReturn(purchase);
        when(calculateServiceImpl.calculatePoints(any(Purchase.class))).thenReturn(28);

        // Send the POST request to store the purchase and capture the returned ID
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(purchaseJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andReturn();

        // Extract the actual ID from the response
        String id = new ObjectMapper().readTree(result.getResponse().getContentAsString()).get("id").asText();

        // Send the GET request to retrieve points
        mockMvc.perform(MockMvcRequestBuilders.get("/receipts/" + id + "/points"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.points").value(28));

        // Verify interactions
        verify(calculateServiceImpl, times(1)).calculatePoints(any(Purchase.class));
    }


    @Test
    void testGetPointsNotFound() throws Exception {
        String id = "invalid-id";

        mockMvc.perform(MockMvcRequestBuilders.get("/receipts/" + id + "/points"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(404));
    }
}
