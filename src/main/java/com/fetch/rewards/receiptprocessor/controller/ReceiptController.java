package com.fetch.rewards.receiptprocessor.controller;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

import com.fetch.rewards.receiptprocessor.service.ICalculateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fetch.rewards.receiptprocessor.entities.Purchase;
import com.fetch.rewards.receiptprocessor.service.CalculateServiceImpl;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    private final ICalculateService calculateServiceImpl;
    private static final Pattern UUID_PATTERN = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$");

    public ReceiptController(CalculateServiceImpl calculateServiceImpl) {
        this.calculateServiceImpl = calculateServiceImpl;
    }

    @PostMapping("/process")
    public Map<String, String> processReceipt(@RequestBody Purchase purchase) {
        String id= calculateServiceImpl.processReceipt(purchase);
        return Map.of("id", id);
    }

    @GetMapping("/{id}/points")
    public Map<String, Integer> getPoints(@PathVariable String id) {

        // Validate if ID is a valid UUID
        if (!UUID_PATTERN.matcher(id).matches()) {
            return Map.of("error",404);
        }
        Purchase purchase = calculateServiceImpl.getPurchaseById(id);

        // check if there is purchase resource for this id
        if (purchase == null) {
           // throw new NoSuchElementException("Receipt not found");
            return Map.of("error",404);
        }
        int points = calculateServiceImpl.calculatePoints(purchase);
        return Map.of("points", points);
    }
}
