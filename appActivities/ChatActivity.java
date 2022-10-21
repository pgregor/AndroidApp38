package com.example.androidproject.appActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.androidproject.R;
import com.example.androidproject.Session;
import com.example.androidproject.messaging.ChatAdapter;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        session = Session.getInstance(getApplicationContext());
        session.makeNavBar(findViewById(R.id.navbar));

        ListView chatList = findViewById(R.id.chats);
        ArrayList<String> chats = session.getMyChats();
        ChatAdapter adapter = new ChatAdapter(this,R.layout.chat,chats);
        chatList.setAdapter(adapter);
        chatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),ChatViewActivity.class);
                intent.putExtra("Chat ID", chats.get(i));
                startActivity(intent);

            }
        });
        ImageButton loginBtn = (ImageButton) findViewById(R.id.userIconMain);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(session.profileClicked(getApplicationContext()));
            }
        });

        ImageButton chatBtn = (ImageButton) findViewById(R.id.chatIcon);
        chatBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(intent);
            }
        });
    }
}