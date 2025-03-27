package com.fetch.rewards.receiptprocessor.repository;

import com.fetch.rewards.receiptprocessor.entities.Purchase;

import java.util.UUID;

public interface IReceiptRepository {
     String save(Purchase purchase);

     Purchase findById(String id);

     boolean existsById(String id);
}
