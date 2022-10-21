package com.example.androidproject.user;

import com.example.androidproject.listing.Listing;

import java.util.LinkedList;

public class ActiveSeller extends ActiveUser {
    private LinkedList<String> listings;
    private LinkedList<String> salesMade;
    private LinkedList<String> followers;

    public ActiveSeller(ActiveUser user) {
        super(user);
        listings = new LinkedList<>();
        salesMade = new LinkedList<>();
    }

    @Override
    public LinkedList<String> getListings() {
        return listings;
    }

    @Override
    public LinkedList<String> getSalesMade() {
        return salesMade;
    }

    public boolean addListing(Listing listing) {
        return listings.add(listing.getID());
    }

    public boolean removeListing(Listing listing) {
        return listings.remove(listing.getID());
    }

    @Override
    public LinkedList<String> getFollowers() {
        return followers;
    }
}
