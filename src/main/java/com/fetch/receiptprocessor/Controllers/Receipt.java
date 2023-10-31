package com.fetch.receiptprocessor.Controllers;

import java.time.LocalDate;
import java.time.LocalTime;
public class Receipt {


    String retailer;
    LocalDate purchaseDate;
    LocalTime purchaseTime;
    Item[] items;
    String total;

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

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

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
