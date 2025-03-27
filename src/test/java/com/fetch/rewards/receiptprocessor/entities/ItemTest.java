package com.fetch.rewards.receiptprocessor.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ItemTest {

    @Test
    void testItemConstructorAndGetters() {
        String description = "Apple";
        double price = 1.99;

        Item item = new Item(description, price);

        assertEquals(description, item.getShortDescription());
        assertEquals(price, item.getPrice(), 0.001);
    }

    @Test
    void testItemSetters() {
        Item item = new Item("OldItem", 0.99);

        String newDescription = "Orange";
        double newPrice = 2.49;

        item.setShortDescription(newDescription);
        item.setPrice(newPrice);

        assertEquals(newDescription, item.getShortDescription());
        assertEquals(newPrice, item.getPrice(), 0.001);
    }

    @Test
    void testToString() {
        Item item = new Item("Banana", 0.59);

        String expectedString = "Item{shortDescription='Banana', price=0.59}";
        assertEquals(expectedString, item.toString());
    }
}
