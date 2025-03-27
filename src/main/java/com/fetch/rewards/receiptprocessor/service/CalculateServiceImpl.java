package com.fetch.rewards.receiptprocessor.service;

import java.time.LocalTime;

import com.fetch.rewards.receiptprocessor.repository.IReceiptRepository;
import com.fetch.rewards.receiptprocessor.repository.ReceiptRepositoryImpl;
import org.springframework.stereotype.Service;

import com.fetch.rewards.receiptprocessor.entities.Item;
import com.fetch.rewards.receiptprocessor.entities.Purchase;

@Service
public class CalculateServiceImpl implements ICalculateService{

    private IReceiptRepository receiptRepositoryImpl;

    public CalculateServiceImpl(ReceiptRepositoryImpl receiptRepositoryImpl) {
        this.receiptRepositoryImpl = receiptRepositoryImpl;
    }

    @Override
    public String processReceipt(Purchase purchase) {
        String id= receiptRepositoryImpl.save(purchase);
        return id;
    }

    @Override
    public Purchase getPurchaseById(String id) {
        Purchase purchase= receiptRepositoryImpl.findById(id);
        return purchase;
    }

    @Override
    public int calculatePoints(Purchase purchase) {
        int points = 0;

        // Rule 1: One point for every alphanumeric character in the retailer name
        points += purchase.getRetailer().replaceAll("[^a-zA-Z0-9]", "").length();

        // Rule 2: 50 points if the total is a round dollar amount with no cents
        if (purchase.getTotal() % 1 == 0) {
            points += 50;
        }

        // Rule 3: 25 points if the total is a multiple of 0.25
        if (purchase.getTotal() % 0.25 == 0) {
            points += 25;
        }

        // Rule 4: 5 points for every two items on the receipt
        points += (purchase.getItems().size() / 2) * 5;

        // Rule 5: If the trimmed length of the item description is a multiple of 3, multiply the price by 0.2 and round up
        for (Item item : purchase.getItems()) {
            if (item.getShortDescription().trim().length() % 3 == 0) {
                points += Math.ceil(item.getPrice() * 0.2);
            }
        }

        // Rule 7: 6 points if the day in the purchase date is odd
        if (purchase.getPurchaseDate().getDayOfMonth() % 2 == 1) {
            points += 6;
        }

        // Rule 8: 10 points if the time of purchase is between 2:00 PM and 4:00 PM
        if (purchase.getPurchaseTime().isAfter(LocalTime.of(14, 0)) && purchase.getPurchaseTime().isBefore(LocalTime.of(16, 0))) {
            points += 10;
        }
        // Rule 6: for llm 5 points?
/*      if(points>10){
            points+=5;
        }*/

        return points;
    }
}
