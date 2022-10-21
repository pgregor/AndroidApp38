package com.example.androidproject;

public class Sale implements ID {
    String seller;
    String buyer;
    String listing;
    String date;

    @Override
    public String getID() {
        return "";
    }

    public String getSeller() {
        return seller;
    }
}
