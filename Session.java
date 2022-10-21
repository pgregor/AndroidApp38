package com.example.androidproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.example.androidproject.listing.Listing;
import com.example.androidproject.user.ActiveSeller;
import com.example.androidproject.user.ActiveUser;
import com.example.androidproject.messaging.Chat;
import com.example.androidproject.user.GuestUser;
import com.example.androidproject.user.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * A singleton class which stores all data and acts as an interface.
 */

public class Session {
    private static Session instance;

    private User currentUser;
    private AVLTree<ActiveUser> users;
    private AVLTree<Listing> listingsIDSorted;
    private AVLTree<Listing> listingsDateSorted;
    private AVLTree<Listing> listingsPriceSorted;
    private AVLTree<Sale> sales;
    private AVLTree<Chat> chats;

    private Session(Context context) {
        currentUser = new GuestUser();
        Gson gson = new Gson();
        JsonReader jsonReader;
        try {
            jsonReader = new JsonReader(new InputStreamReader(
                    context.getAssets().open("user_data.json")
            ));
            users = gson.fromJson(jsonReader
                    , new TypeToken<AVLTree<ActiveUser>>(){}.getType()
            );
            jsonReader = new JsonReader(new InputStreamReader(
                    context.openFileInput("listings_id.json")
            ));
            listingsIDSorted = gson.fromJson(jsonReader
                    , new TypeToken<AVLTree<Listing>>(){}.getType()
            );
            jsonReader = new JsonReader(new InputStreamReader(
                    context.openFileInput("listings_price.json")
            ));
            listingsPriceSorted = gson.fromJson(jsonReader
                    , new TypeToken<AVLTree<Listing>>(){}.getType()
            );
            jsonReader = new JsonReader(new InputStreamReader(
                    context.openFileInput("listings_date.json")
            ));
            listingsDateSorted = gson.fromJson(jsonReader
                    , new TypeToken<AVLTree<Listing>>(){}.getType()
            );
            jsonReader = new JsonReader(new InputStreamReader(
                    context.openFileInput("sales.json")
            ));
            sales = gson.fromJson(jsonReader
                    , new TypeToken<AVLTree<Sale>>(){}.getType()
            );
            jsonReader = new JsonReader(new InputStreamReader(
                    context.openFileInput("chats.json")
            ));
            chats = gson.fromJson(jsonReader
                    , new TypeToken<AVLTree<Chat>>(){}.getType()
            );
            jsonReader.close();
        } catch (IOException e) {
            e.printStackTrace();
            users = new AVLTree.EmptyAVL<>();
            listingsIDSorted = new AVLTree.EmptyAVL<>();
            listingsPriceSorted = new AVLTree.EmptyAVL<>();
            listingsDateSorted = new AVLTree.EmptyAVL<>();
            sales = new AVLTree.EmptyAVL<>();
            chats = new AVLTree.EmptyAVL<>();
        }
    }

    public Session() {
        currentUser = new GuestUser();
        users = new AVLTree.EmptyAVL<>();
        listingsIDSorted = new AVLTree.EmptyAVL<>();
        listingsPriceSorted = new AVLTree.EmptyAVL<>();
        listingsDateSorted = new AVLTree.EmptyAVL<>();
        sales = new AVLTree.EmptyAVL<>();
        chats = new AVLTree.EmptyAVL<>();
    }

    public static Session getInstance(Context context) {
        if (instance==null) {
            instance = new Session(context);
        }
        return instance;
    }

    public static Session getInstance() {
        if (instance==null) {
            instance = new Session();
        }
        return instance;
    }

    /**
     *
     * @return the id of the current user.
     */

    public String getCurrentUser() {
        return currentUser.getUsername();
    }

    /**
     *
     * @param username
     * @param password
     * @return whether or not the login was successful
     */

    public boolean login(String username, String password) {
        ActiveUser user = users.get(username);
        if (user!=null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * replaces the current user with a guest user.
     */

    public void logout() {
        currentUser = new GuestUser();
    }

    public void makeNavBar(View view) {
        currentUser.makeNavBar(view);
        for (String a : getMyChats()) {
            Chat chat = chats.get(a);
//            if (chat.containsUnread()) view;
        }
    }

    /**
     *
     * @return the intent resulting from the profile being clicked based on the current user.
     */

    public Intent profileClicked(Context context) {
        return currentUser.profileClicked(context);
    }

    /**
     *
     * @return the current users chats.
     */

    public ArrayList<String> getMyChats() {
        return currentUser.getChats();
    }

    /**
     *
     * @param chat chat to find the recipient of.
     * @return the chat participant which is not the current user.
     */

    public String getRecipient(Chat chat) {
        for (String a : chat.getParticipants()) {
            if (!a.equals(currentUser.getUsername())) return a;
        }
        return null;
    }

    /**
     *
     * @param chat chat to be added to.
     * @param message message to be added.
     */

    public void sendMessage(Chat chat, String message) {
        if (message.equals("")) return;
        if (checkMessageAllowed(getRecipient(chat))) {
            chat.addMessage(new Chat.Message(getCurrentUser(), message));
            if (getChat(chat.getID())==null) {
                createChat(chat);
            }
        }
    }

    /**
     *
     * @param recipient the person the chat is being opened with.
     * @return a new chat between the current user and recipient or an existing one if one already
     * exists.
     */

    public Chat chatWith(String recipient) {
        for (String a : currentUser.getChats()) {
            Chat chat = chats.get(a);
            if (getRecipient(chat).equals(recipient)) return chat;
        }
        if (checkMessageAllowed(recipient)) {
            return new Chat(getCurrentUser(),recipient);
        }
        return null;
    }

    /**
     *
     * @param recipient the person to whom the message is directed.
     * @return true if the recipient has not blocked the user and has preferences which allow the
     * chat.
     */

    public boolean checkMessageAllowed(String recipient) {
        ActiveUser user = users.get(recipient);
        if (user.getBlocked().contains(getCurrentUser())) {
            return false;
        }
        boolean[] preferences = user.getMessagingPreferences();
        if (preferences[3]
                || (preferences[0] && currentUser.getFollowers().contains(recipient))) {
            return true;
        }
        if (preferences[1]) {
            for (String a : currentUser.getPurchases()) {
                Sale sale = sales.get(a);
                if (sale.getSeller().equals(recipient))
                    return true;
            }
        }
        if (preferences[2]) {
            for (String a : user.getWatchList()) {
                if (listingsIDSorted.get(a).getSellerId().equals(getCurrentUser()))
                    return true;
            }
        }
        return false;
    }

    /**
     * get chat by id.
     */

    public Chat getChat(String chat) {
        return chats.get(chat);
    }

    /**
     * adds a chat to the main chat tree and to the chat lists of both participants.
     */

    public void createChat(Chat chat) {
        chats = chats.insert(chat);
        for (String a : chat.getParticipants()) {
            users.get(a).addChat(chat);
        }
    }

    public void blockUser(String user) {
        currentUser.blockUser(user);
    }

    /**
     *
     * @return true if user is in blocked list.
     */
    public boolean isBlocked(String user) {
        return currentUser.getBlocked().contains(user);
    }

    public void setMessagingPreferences(boolean following, boolean bought, boolean interested, boolean everyone) {
        currentUser.setMessagingPreferences(following, bought, interested, everyone);
    }

    /**
     * modifies switch views to reflect current user's messaging preferences.
     */

    public void setFromPreferences(Switch following, Switch bought, Switch interested, Switch everyone) {
        boolean[] preferences = currentUser.getMessagingPreferences();
        following.setChecked(preferences[0]);
        bought.setChecked(preferences[1]);
        interested.setChecked(preferences[2]);
        everyone.setChecked(preferences[3]);
    }

    /**
     *
     * @return user's profile picture.
     */
    public Drawable getUserPic(Context context, String username) {
        try{
            InputStream is = context.getAssets().open(users.get(username).getProfilePic());
            return Drawable.createFromStream(is, null);
        }
        catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * creates a new user and adds it to the user tree (username must be unique);
     * @return true if creation was successful.
     */

    public boolean createUser(String username, String password) {
        ActiveUser user = new ActiveUser(username, password);
        AVLTree<ActiveUser> newTree = users.insert(user);
        if (newTree==users) return false;
        users = newTree;
        return true;
    }

    public void buildProfile(String userID, View details, Button logoutButton) {
        if (userID.equals(getCurrentUser())) {
            logoutButton.setVisibility(View.VISIBLE);
            details.findViewById(R.id.message_button).setVisibility(View.GONE);
            details.findViewById(R.id.messaging_preferences).setVisibility(View.VISIBLE);
        }
    }

    public void createListing() {
        Listing listing = new Listing();
        currentUser.getListings().add(listing.getID());
        listingsIDSorted = listingsIDSorted.insert(listing);
        listingsPriceSorted = listingsPriceSorted.insert(listing);
        listingsDateSorted = listingsDateSorted.insert(listing);
    }

    public void makeSale(String listingID) {
        Listing listing = listingsIDSorted.get(listingID);
        ActiveUser seller  = users.get(listing.getSellerId());
        Sale sale = new Sale();
        currentUser.getPurchases().add(sale.getID());
        seller.getSalesMade().add(sale.getID());
        sales.insert(sale);
        listing.setItemAvailability(false);
    }

    public void upgradeUser() {
        ActiveSeller newSeller = currentUser.upGrade();
        if (newSeller!=null) {
            users = users.remove(getCurrentUser());
            users = users.insert(newSeller);
            currentUser = newSeller;
        }
    }

    /**
     * gets rid of instance, effectively erasing session data. Useful for testing.
     */

    public void killSession() {
        instance = null;
    }
}
