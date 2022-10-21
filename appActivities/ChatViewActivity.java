package com.example.androidproject.appActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.androidproject.R;
import com.example.androidproject.Session;
import com.example.androidproject.messaging.Chat;
import com.example.androidproject.messaging.MessageAdapter;

import java.util.ArrayList;

public class ChatViewActivity extends AppCompatActivity {
    Session session;
    Chat chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_view);
        session = Session.getInstance(getApplicationContext());

        String chatID = getIntent().getStringExtra("Chat ID");
        String recipient = getIntent().getStringExtra("Recipient");
        if (chatID!=null) {
            chat = session.getChat(chatID);
            recipient = session.getRecipient(chat);
        } else chat = session.chatWith(recipient);
        setTitle("Chatting with "+recipient+(session.isBlocked(recipient)?" (BLOCKED)":""));
        ArrayList<Chat.Message> messages = chat.getMessages();
        ListView messageList = findViewById(R.id.messages);
        MessageAdapter adapter = new MessageAdapter(this
                , R.layout.message, messages);
        messageList.setAdapter(adapter);
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView messageView = findViewById(R.id.message);
                if (!messageView.getText().equals("")) {
                    if (chat.getMessages().isEmpty()) session.createChat(chat);
                    session.sendMessage(chat,messageView.getText()+"");
                    messageView.setText("");
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}