package com.example.androidproject.messaging;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.androidproject.R;
import com.example.androidproject.Session;

import java.util.ArrayList;

public class ChatAdapter extends ArrayAdapter<String> {

    public ChatAdapter(@NonNull Context context, int resource, ArrayList<String> chats) {
        super(context, resource, chats);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat, parent, false);

            /**
             * Get UI elements
             */
            TextView name = convertView.findViewById(R.id.recipient);
            TextView messageText = convertView.findViewById(R.id.message_preview);
            ImageView picture = convertView.findViewById(R.id.picture);

            /**
             * Set properties of UI elements from chats array
             */

            Chat chat = Session.getInstance().getChat(super.getItem(position));
            Session session = Session.getInstance(getContext());
            String recipient = session.getRecipient(chat);
            name.setText(recipient);
            if (chat.containsUnread()) name.setTypeface(null, Typeface.BOLD);
            ArrayList<Chat.Message> messages = chat.getMessages();
            messageText.setText(messages.get(messages.size()-1).preview());
            if (chat.containsUnread()) messageText.setTypeface(null, Typeface.BOLD);
            Drawable d = session.getUserPic(getContext(),recipient);
            if (d!=null) picture.setImageDrawable(d);
        }
        return convertView;
    }

}