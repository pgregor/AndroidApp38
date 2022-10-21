package com.example.androidproject.appActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.example.androidproject.R;
import com.example.androidproject.Session;

public class MessagingPreferencesActivity extends AppCompatActivity {
    Session session;
    Switch boughtSwitch;
    Switch followingSwitch;
    Switch interestedSwitch;
    Switch everyoneSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging_preferences);
        session = Session.getInstance(getApplicationContext());
        boughtSwitch = findViewById(R.id.bought_switch);
        followingSwitch = findViewById(R.id.following_switch);
        interestedSwitch = findViewById(R.id.interested_switch);
        everyoneSwitch = findViewById(R.id.everyone_switch);
        session.setFromPreferences(boughtSwitch,followingSwitch,interestedSwitch,everyoneSwitch);
    }

    public void setPreferences(View view) {
        session.setMessagingPreferences(boughtSwitch.isChecked(), followingSwitch.isChecked()
                , interestedSwitch.isChecked(), everyoneSwitch.isChecked());
        finish();
    }
}