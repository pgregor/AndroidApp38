package com.example.androidproject.listing;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AddListingDataStream {

    public void addNewListingFromBacklog(Context context) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                JsonReader jsonReader;
                ArrayList<Listing> listings;
                try {
                    jsonReader = new JsonReader(new InputStreamReader(
                            context.getAssets().open("listing_data_backlog.json")
                    ));
                    listings = gson.fromJson(jsonReader
                            , new TypeToken<ArrayList<Listing>>() {
                            }.getType()
                    );
                    Listing backlogListing = listings.get(0);
                    backlogListing.setDateTimeListed();
                    gson.toJson(backlogListing, new FileWriter("listing_data.json"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(runnable, 0, 30, TimeUnit.SECONDS);

    }
}
