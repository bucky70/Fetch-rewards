package com.fetch.rewards.receiptprocessor.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

class PurchaseTest {

    @Test
    void testPurchaseConstructorAndGetters() {
        String retailer = "RetailerX";
        LocalDate purchaseDate = LocalDate.of(2024, 3, 25);
        LocalTime purchaseTime = LocalTime.of(14, 30);
        double total = 100.50;
        List<Item> items = Collections.singletonList(new Item("Item1", 50));

        Purchase purchase = new Purchase(retailer, purchaseDate, purchaseTime, total, items);

        assertEquals(retailer, purchase.getRetailer());
        assertEquals(purchaseDate, purchase.getPurchaseDate());
        assertEquals(purchaseTime, purchase.getPurchaseTime());
        assertEquals(total, purchase.getTotal(), 0.001);
        assertEquals(items, purchase.getItems());
    }

    @Test
    void testPurchaseSetters() {
        Purchase purchase = new Purchase();

        String retailer = "NewRetailer";
        LocalDate purchaseDate = LocalDate.of(2024, 4, 1);
        LocalTime purchaseTime = LocalTime.of(15, 45);
        double total = 250.75;
        List<Item> items = Collections.singletonList(new Item("NewItem", 75));

        purchase.setRetailer(retailer);
        purchase.setPurchaseDate(purchaseDate);
        purchase.setPurchaseTime(purchaseTime);
        purchase.setTotal(total);
        purchase.setItems(items);

        assertEquals(retailer, purchase.getRetailer());
        assertEquals(purchaseDate, purchase.getPurchaseDate());
        assertEquals(purchaseTime, purchase.getPurchaseTime());
        assertEquals(total, purchase.getTotal(), 0.001);
        assertEquals(items, purchase.getItems());
    }

    @Test
    void testToString() {
        Purchase purchase = new Purchase("TestRetailer", LocalDate.of(2024, 3, 25), LocalTime.of(14, 30), 99.99, Collections.emptyList());

        String expectedString = "Purchase{retailer='TestRetailer', purchaseDate=2024-03-25, purchaseTime=14:30, total=99.99, items=[]}";
        assertEquals(expectedString, purchase.toString());
    }
}

