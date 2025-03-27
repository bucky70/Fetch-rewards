package com.fetch.rewards.receiptprocessor.service;

import com.fetch.rewards.receiptprocessor.entities.Purchase;

public interface ICalculateService {
     String processReceipt(Purchase purchase);

     Purchase getPurchaseById(String id);

     int calculatePoints(Purchase purchase);
}