package com.example.androidproject.user;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.androidproject.ID;
import com.example.androidproject.messaging.Chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public abstract class User implements Serializable, ID {
    private final String id;

    public User(String id) {
        this.id = id;
    }

    @Override
    public String getID() {
        return id;
    }

    public abstract String getUsername();

    public abstract Intent profileClicked(Context context);

    public abstract void makeNavBar(View view);

    public abstract LinkedList<String> getListings();

    public abstract LinkedList<String> getPurchases();

    public abstract LinkedList<String> getSalesMade();

    public abstract ArrayList<String> getChats();

    public abstract LinkedList<String> getFollowers();

    public abstract void addChat(Chat chat);

    public abstract void blockUser(String user);

    public abstract LinkedList<String> getBlocked();

    public abstract LinkedList<String> getWatchList();

    public abstract ActiveSeller upGrade();

    public abstract boolean[] getMessagingPreferences();

    public abstract void setMessagingPreferences(boolean value1, boolean value2, boolean value3, boolean value4);
}
