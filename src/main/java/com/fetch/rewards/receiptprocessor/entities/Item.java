package com.fetch.rewards.receiptprocessor.entities;

public class Item {
    private String shortDescription;
    private double price;

    public Item(){

    }
    public Item(String shortDescription, double price) {
        this.shortDescription = shortDescription;
        this.price = price;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "shortDescription='" + shortDescription + '\'' +
                ", price=" + price +
                '}';
    }
}


