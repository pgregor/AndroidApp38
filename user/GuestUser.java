package com.example.androidproject.user;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.androidproject.appActivities.LoginActivity;
import com.example.androidproject.messaging.Chat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;

public class GuestUser extends User {
    public GuestUser() {
        super(UUID.randomUUID().toString());
    }

    @Override
    public String getUsername() {
        return "GuestUser";
    }

    /**
     * directs app to login activity.
     */
    @Override
    public Intent profileClicked(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public void makeNavBar(View view) {
    }

    @Override
    public LinkedList<String> getListings() {
        return new LinkedList<>();
    }

    @Override
    public LinkedList<String> getPurchases() {
        return new LinkedList<>();
    }

    @Override
    public LinkedList<String> getSalesMade() {
        return new LinkedList<>();
    }

    @Override
    public ArrayList<String> getChats() {
        return new ArrayList<>();
    }

    @Override
    public LinkedList<String> getFollowers() {
        return new LinkedList<>();
    }

    @Override
    public void addChat(Chat chat) {
    }

    @Override
    public void blockUser(String user) {

    }

    @Override
    public LinkedList<String> getBlocked() {
        return null;
    }

    @Override
    public LinkedList<String> getWatchList() {
        return null;
    }

    @Override
    public ActiveSeller upGrade() {
        return null;
    }

    @Override
    public boolean[] getMessagingPreferences() {
        return null;
    }

    @Override
    public void setMessagingPreferences(boolean value1, boolean value2, boolean value3, boolean value4) {

    }
}
