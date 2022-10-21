package com.example.androidproject.messaging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.androidproject.R;
import com.example.androidproject.Session;
import com.example.androidproject.messaging.Chat;

import java.util.ArrayList;

public class MessageAdapter extends ArrayAdapter<Chat.Message> {

    public MessageAdapter(@NonNull Context context, int resource, ArrayList<Chat.Message> messages) {
        super(context, resource, messages);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.message, parent, false);

            /**
             * Get UI elements
             */
            TextView sender = convertView.findViewById(R.id.sender);
            TextView messageText = convertView.findViewById(R.id.message_text);
            TextView timeSent = convertView.findViewById(R.id.time_sent);

            /**
             * Set properties of UI elements from listingsArray
             */
            Chat.Message message = super.getItem(position);
            message.read();
            sender.setText(
                    (message.getSender().equals(Session.getInstance().getCurrentUser())
                            ?"You":message.getSender())
                    +":");
            messageText.setText(message.getMessage());
            timeSent.setText(message.getTimeSent());
        }
        return convertView;
    }

}