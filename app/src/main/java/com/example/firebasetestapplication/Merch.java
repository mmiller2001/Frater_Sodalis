package com.example.firebasetestapplication;

public class Merch {

    public String getMerch_title() {
        return merch_title;
    }

    public void setMerch_title(String merch_title) {
        this.merch_title = merch_title;
    }

    public String getMerch_description() {
        return merch_description;
    }

    public void setMerch_description(String merch_description) {
        this.merch_description = merch_description;
    }

    public String getMerch_price() {
        return merch_price;
    }

    public void setMerch_price(String merch_price) {
        this.merch_price = merch_price;
    }

    public String getMerch_item() {
        return merch_item;
    }

    public void setMerch_item(String merch_item) {
        this.merch_item = merch_item;
    }

    String merch_item;
    String merch_title;
    String merch_description;
    String merch_price;

    public Merch(String merch_item, String merch_title, String merch_description, String merch_price) {
        this.merch_item = merch_item;
        this.merch_title = merch_title;
        this.merch_description = merch_description;
        this.merch_price = merch_price;
    }
}
