package com.example.androidproject.appActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidproject.R;
import com.example.androidproject.Session;

public class LoginActivity extends AppCompatActivity {
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = Session.getInstance(getApplicationContext());

        Intent closeIntent = new Intent(this, MainActivity.class);
        ImageButton closeBtn = (ImageButton) findViewById(R.id.closeButton);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(closeIntent);
            }
        });

    }

    protected void onPause(Bundle savedInstanceState) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
        TextView username = findViewById(R.id.username);
        TextView password = findViewById(R.id.password);
        if (session.login(username.getText()+"",password.getText()+"")) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast toast = Toast.makeText(getApplicationContext()
                    ,"Username or password is incorrect", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


}