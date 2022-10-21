package com.example.androidproject.messaging;

import com.example.androidproject.ID;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

public class Chat implements ID {
    private final String id;
    private String[] participants;
    private ArrayList<Message> messages;

    public Chat(String participant1, String participant2) {
        id = UUID.randomUUID().toString();
        messages = new ArrayList<>();
        participants = new String[]{participant1, participant2};
    }

    public String[] getParticipants() {
        return participants;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public boolean addMessage(Message message) {
        return messages.add(message);
    }

    /**
     *
     * @return true if there are messages within the chat that are unread.
     */

    public boolean containsUnread() {
        for (Message a : messages) {
            if (a.read) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return getMessages().toString();
    }

    @Override
    public String getID() {
        return id;
    }

    public static class Message {
        private final String sender;
        private final String message;
        private boolean read;
        private final String timeSent;

        public Message(String sender, String message) {
            this.sender = sender;
            this.message = message;
            read = false;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm");
                LocalDateTime now = LocalDateTime.now();
                timeSent = dtf.format(now);
            } else timeSent = null;
        }

        public String getSender() {
            return sender;
        }

        public String getMessage() {
            return message;
        }

        public String getTimeSent() {
            return timeSent;
        }

        public void read() {
            read = true;
        }

        /**
         *
         * @return only the first few characters of the message.
         */
        public String preview() {
            return message.length()>20?message.substring(0,20)+"...":message;
        }

        @Override
        public String toString() {
            return getSender() + ": "+ getMessage();
        }
    }
}
