package com.example.androidproject.search;

import android.content.Context;

import com.example.androidproject.listing.Item;
import com.example.androidproject.listing.Listing;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SearchResults {

public ArrayList<Listing> getListings(Context context) {
    ArrayList<Listing> listings;
    Gson gson = new Gson();
    JsonReader jsonReader = null;
    try {
        jsonReader = new JsonReader(new InputStreamReader(
                context.getAssets().open("listing_data.json")
        ));
        listings = gson.fromJson(jsonReader
                , new TypeToken<ArrayList<Listing>>() {
                }.getType()
        );
    } catch (
            IOException e) {
        e.printStackTrace();
        listings = null;
    }
    int n = listings.size();
    for (int i=0; i<n; i++) {
        listings.get(i).setDateTimeListed();
    }
    return listings;
}

//public ArrayList<Item> getItems(Context context) {
//    ArrayList<Item> items;
//    Gson gson = new Gson();
//    JsonReader jsonReader = null;
//    try {
//    jsonReader = new JsonReader(new InputStreamReader(
//            context.getAssets().open("item_data.json")
//    ));
//    items = gson.fromJson(jsonReader
//            , new TypeToken<ArrayList<Item>>(){}.getType()
//    );
//} catch (IOException e) {
//    e.printStackTrace();
//    items = null;
//}
//    return items;
//}

}
