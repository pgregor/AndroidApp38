package com.example.androidproject.user;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.androidproject.appActivities.ProfileActivity;
import com.example.androidproject.R;
import com.example.androidproject.messaging.Chat;

import java.util.ArrayList;
import java.util.LinkedList;

public class ActiveUser extends User {
    private String username;
    private String profilePic;
    private String password;
    private ArrayList<String> chats;
    private LinkedList<String> blocked;
    private boolean[] messagingPreferences;
    private LinkedList<String> buys;
    private LinkedList<String> watchList;

    public ActiveUser(String username, String password) {
        super(username);
        this.username = username;
        this.password = password;
        chats = new ArrayList<>();
        blocked = new LinkedList<>();
        messagingPreferences = new boolean[]{true, true, true, true};
        buys = new LinkedList<>();
        watchList = new LinkedList<>();
    }

    public ActiveUser(ActiveUser user) {
        super(user.getUsername());
        username = user.getUsername();
        chats = user.getChats();
        blocked = user.getBlocked();
        messagingPreferences = user.getMessagingPreferences();
        buys = user.getPurchases();
        watchList = user.getWatchList();
    }

    public String getPassword() {
        return password;
    }

    public String getProfilePic() {
        return profilePic;
    }

    @Override
    public LinkedList<String> getWatchList() {
        return watchList;
    }

    @Override
    public ActiveSeller upGrade() {
        return new ActiveSeller(this);
    }

    @Override
    public LinkedList<String> getFollowers() {
        return new LinkedList<>();
    }

    @Override
    public String getUsername() {
        return username;
    }

    /**
     * directs app to view profile activity.
     */
    @Override
    public Intent profileClicked(Context context) {
        Intent intent = new Intent(context,ProfileActivity.class);
        intent.putExtra("User", getUsername());
        return intent;
    }

    /**
     * sets chat icon to visible in navbar.
     * @param view navbar
     */

    @Override
    public void makeNavBar(View view) {
        view.findViewById(R.id.chatIcon).setVisibility(View.VISIBLE);
    }

    @Override
    public LinkedList<String> getListings() {
        return new LinkedList<>();
    }

    @Override
    public LinkedList<String> getPurchases() {
        return buys;
    }

    @Override
    public LinkedList<String> getSalesMade() {
        return new LinkedList<>();
    }

    @Override
    public ArrayList<String> getChats() {
        return chats;
    }

    @Override
    public void addChat(Chat chat) {
        chats.add(chat.getID());
    }

    @Override
    public void blockUser(String user) {
        blocked.add(user);
    }

    @Override
    public LinkedList<String> getBlocked() {
        return blocked;
    }

    @Override
    public boolean[] getMessagingPreferences() {
        return messagingPreferences;
    }

    @Override
    public void setMessagingPreferences(boolean value1, boolean value2, boolean value3, boolean value4) {
        messagingPreferences = new boolean[]{value1, value2, value3, value4};
    }

    @Override
    public String toString() {
        return username;
    }
}