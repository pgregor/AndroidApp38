package com.example.androidproject.listing;


import com.example.androidproject.ID;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Listing implements ID {
    private String id;
    private String sellerId;
    private Double price;
    private Boolean visibility;
    private String dateTimeListed;
    private Integer itemId;
    private Item.Type itemType;
    private String itemName;
    private String itemDescription;
    private ArrayList<String> itemImageFiles;
    private Item.Condition itemCondition;
    private ArrayList<Item.Style> itemStyles;
    private ArrayList<Item.Material> itemMaterials;
    private Boolean itemAvailability;


    // TODO to write need constructors for CreateListingActivity
//    public Listing() {
//        this.id = null;
//        this.itemId = null;
//        this.sellerId = null;
//        this.price = null;
//        this.dateTimeListed = "";
//        this.visibility = false;
//    }

    /**
     * Getters
     */

    public String getSellerId() {
        return sellerId;
    }

    public Double getPrice() {
        return price;
    }

    public String getDateTimeListed() {
        return dateTimeListed;
    }

    public Boolean getVisibility() {
        return visibility;
    }

    public Integer getItemId() {
        return itemId;
    }

    public Item.Type getItemType() {
        return itemType;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public ArrayList<String> getItemImageFiles() {
        return itemImageFiles;
    }

    public Item.Condition getItemCondition() {
        return itemCondition;
    }

    public ArrayList<Item.Style> getItemStyles() {
        return itemStyles;
    }

    public ArrayList<Item.Material> getItemMaterials() {
        return itemMaterials;
    }

    public Boolean getItemAvailability() {
        return itemAvailability;
    }

    public void setDateTimeListed() {
            // Call requires API level 26 (current min is 22): java.time.format.DateTimeFormatter#ofPattern
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                this.dateTimeListed = dtf.format(now);
            }
    }

    public void setItemAvailability(Boolean itemAvailability) {
        this.itemAvailability = itemAvailability;
    }

    @Override
    public String getID() {
        return id;
    }
}

