package com.example.androidproject.appActivities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidproject.R;

import java.io.IOException;
import java.io.InputStream;

public class ListingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);

        Intent intent = getIntent();
        TextView listingName = findViewById(R.id.listingName);
        TextView listingCategory = findViewById(R.id.listingCategory);
        TextView listingPrice = findViewById(R.id.listingPrice);
        TextView listingCondition = findViewById(R.id.listingCondition);
        TextView listingDescription = findViewById(R.id.listingDescription);
        TextView listingDate = findViewById(R.id.listingDate);
        TextView listingSeller = findViewById(R.id.listingSeller);
        TextView listingStyles = findViewById(R.id.listingStyles);
        TextView listingMaterials = findViewById(R.id.listingMaterials);
        ImageView listingImage = findViewById(R.id.listingImage);

        String name = intent.getStringExtra("name");
        String category   = intent.getStringExtra("category");
        String price = intent.getStringExtra("price");
        String condition = intent.getStringExtra("condition");
        String description = intent.getStringExtra("description");
        String style = intent.getStringExtra("style");
        String material = intent.getStringExtra("material");
        String dateTimeListed = intent.getStringExtra("dateListed");
        String dateListed = dateTimeListed.substring(0,10);
        String seller = intent.getStringExtra("seller");
        String imageFileName = intent.getStringExtra("imageFileName");

        try {
            InputStream is = getAssets().open("listing_images/" + imageFileName);
            Drawable d = Drawable.createFromStream(is, null);
            listingImage.setImageDrawable(d);
            }
        catch(IOException e) {
            e.printStackTrace();
        }

        listingName.setText(name);
        listingCategory.setText("Category: " + category);
        listingPrice.setText("Price: $" + price);
        listingCondition.setText("Condition: " + condition);
        listingDescription.setText(description);
        listingDate.setText("Listed: " + dateListed + "   By: ");
        listingSeller.setText(seller);
        listingStyles.setText("Styles: " + style);
        listingMaterials.setText("Materials: " + material);

        Intent backToMainIntent = new Intent(this, MainActivity.class);
        ImageButton listingBackButton = (ImageButton) findViewById(R.id.listingBackButton);
        listingBackButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(backToMainIntent);
            }
        });

        listingSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                intent.putExtra("User", seller);
                startActivity(intent);
            }
        });
    }
}

