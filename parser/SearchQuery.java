package com.example.androidproject.parser;

import com.example.androidproject.listing.Listing;

import java.util.ArrayList;

public abstract class SearchQuery {
    public abstract String show();
    public abstract ArrayList<Listing> search(ArrayList<Listing> list);
}
