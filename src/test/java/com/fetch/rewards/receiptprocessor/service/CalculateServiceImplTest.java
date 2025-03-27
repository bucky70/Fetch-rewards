package com.fetch.rewards.receiptprocessor.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fetch.rewards.receiptprocessor.entities.Item;
import com.fetch.rewards.receiptprocessor.entities.Purchase;
import com.fetch.rewards.receiptprocessor.repository.ReceiptRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CalculateServiceImplTest {

    @Mock
    private ReceiptRepositoryImpl receiptRepositoryImpl;

    @InjectMocks
    private CalculateServiceImpl calculateServiceImpl;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void testCalculatePointsFromJson() throws Exception {
        // Load test data from JSON file
        String purchaseJson = new String(Files.readAllBytes(Paths.get("src/test/resources/data/TestData1.json")));
        Purchase purchase = objectMapper.readValue(purchaseJson, Purchase.class);

        // Call the calculatePoints method
        int points = calculateServiceImpl.calculatePoints(purchase);

        // Validate expected points based on business rules
        System.out.println("Calculated Points: " + points);

        // Example assertions based on business logic
        assertTrue(points > 0, "Points should be greater than 0");
        assertEquals(28, points, "Points should match expected value based on rules");
    }

    @Test
    void testCalculatePointsWithEdgeCase() {
        // Create edge-case data (e.g., no items, special time conditions)
        Purchase purchase = new Purchase(
                "EdgeCaseStore",
                LocalDate.of(2023, 12, 5), // Odd day for extra points
                LocalTime.of(14, 30),      // 2:30 PM for bonus points
                50.0,                      // Round number for bonus points
                Collections.singletonList(new Item("AAA", 20.0)) // Item with desc length multiple of 3
        );

        int points = calculateServiceImpl.calculatePoints(purchase);

        System.out.println("Edge Case Points: " + points);

        // Validate against the rules
        assertEquals(108, points, "Edge-case points should match the expected total.");
    }

    @Test
    void testZeroPoints() {
        // Minimal purchase (no special conditions)
        Purchase purchase = new Purchase(
                "NoBonus",
                LocalDate.of(2023, 11, 4), // Even day (no extra points)
                LocalTime.of(10, 0),      // Outside bonus hours
                12.34,                    // No round number
                Collections.emptyList()   // No items
        );

        int points = calculateServiceImpl.calculatePoints(purchase);

        System.out.println("Zero Points Case: " + points);

        assertEquals(7, points, "Only retailer character points should be counted.");
    }
}
