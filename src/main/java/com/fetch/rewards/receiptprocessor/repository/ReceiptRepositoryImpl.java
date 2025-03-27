package com.fetch.rewards.receiptprocessor.repository;

import com.fetch.rewards.receiptprocessor.entities.Purchase;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ReceiptRepositoryImpl implements IReceiptRepository{

    private final Map<String, Purchase> receiptStore = new ConcurrentHashMap<>();

    // Save and return generated ID
    @Override
    public String save(Purchase purchase) {
        String id = UUID.randomUUID().toString();
        receiptStore.put(id, purchase);
        return id;
    }

    // Retrieve a purchase by ID
    @Override
    public Purchase findById(String id) {
        return receiptStore.get(id);
    }

    // Check if a purchase exists
    @Override
    public boolean existsById(String id) {
        return receiptStore.containsKey(id);
    }
}
