package com.fetch.rewards.receiptprocessor.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

//@Getter
//@Setter
public class Purchase {

    private String retailer;
    private LocalDate purchaseDate;
    private LocalTime purchaseTime;
    private double total;
    private List<Item> items;

    public Purchase() {}

    public Purchase(String retailer, LocalDate purchaseDate, LocalTime purchaseTime, double total, List<Item> items) {
        this.retailer = retailer;
        this.purchaseDate = purchaseDate;
        this.purchaseTime = purchaseTime;
        this.total = total;
        this.items = items;
    }

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalTime getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(LocalTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "retailer='" + retailer + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", purchaseTime=" + purchaseTime +
                ", total=" + total +
                ", items=" + items +
                '}';
    }


}