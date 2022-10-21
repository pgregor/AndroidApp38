package com.example.androidproject.appActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidproject.R;
import com.example.androidproject.Session;

public class ProfileActivity extends AppCompatActivity {
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        session = Session.getInstance(getApplicationContext());
        session.makeNavBar(findViewById(R.id.navbar));

        String user = getIntent().getStringExtra("User");
        setTitle(user);
        View view = findViewById(R.id.details);
        Button logoutButton = findViewById(R.id.logout);
        session.buildProfile(user, view, logoutButton);
        ImageView profilePic = findViewById(R.id.profile_picture);
        profilePic.setImageDrawable(session.getUserPic(getApplicationContext(), user));
        TextView name = findViewById(R.id.name);
        name.setText(user);

        ImageButton chatBtn = (ImageButton) findViewById(R.id.chatIcon);
        chatBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(intent);
            }
        });

        ImageButton loginBtn = (ImageButton) findViewById(R.id.userIconMain);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(session.profileClicked(getApplicationContext()));
            }
        });
    }

    public void logout(View view) {
        session.logout();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void messagingPreferences(View view) {
        Intent intent = new Intent(this, MessagingPreferencesActivity.class);
        startActivity(intent);
    }

    public void message(View view) {
        Intent intent = new Intent(this, ChatViewActivity.class);
        intent.putExtra("Recipient",getIntent().getStringExtra("User"));
        startActivity(intent);
    }
}