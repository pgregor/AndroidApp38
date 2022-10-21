package com.example.androidproject.appActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.androidproject.R;
import com.example.androidproject.Session;
import com.example.androidproject.listing.Item;
import com.example.androidproject.listing.Listing;
import com.example.androidproject.parser.Parser;
import com.example.androidproject.parser.SearchQuery;
import com.example.androidproject.search.SearchResultAdapter;
import com.example.androidproject.search.SearchResults;
import com.example.androidproject.tokenizer.Tokenizer;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Session session;

    private ArrayList<Listing> allListings;
    private ArrayList<Listing> listings;
    private ArrayList<Item> items;

    private ListView searchResultsListView;
    private ArrayAdapter<Listing> listingArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = Session.getInstance(getApplicationContext());
        session.makeNavBar(findViewById(R.id.navbar));

        listings = new SearchResults().getListings(getApplicationContext());
        allListings = new ArrayList<>(listings);
        listingArrayAdapter = new SearchResultAdapter(getApplicationContext(), R.layout.listing, listings);
        searchResultsListView = findViewById(R.id.searchResultsListView);
        searchResultsListView.setAdapter(listingArrayAdapter);

        Intent loginIntent = new Intent(this, LoginActivity.class);
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

        Intent listingIntent = new Intent(this, ListingActivity.class);
        ListView searchResults = (ListView) findViewById(R.id.searchResultsListView);
        searchResults.setClickable(true);
        searchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Listing listing = listings.get(position);
//                Item item = new Item().getItemFromListingId(getApplicationContext(),listing.getItemId());

                listingIntent.putExtra("name", listing.getItemName());
                listingIntent.putExtra("category", listing.getItemType().toString());
                listingIntent.putExtra("price", Double.toString(listing.getPrice()));
                listingIntent.putExtra("condition", listing.getItemCondition().toString());
                listingIntent.putExtra("description", listing.getItemDescription());
                listingIntent.putExtra("material", listing.getItemMaterials().get(0).toString());
                listingIntent.putExtra("style", listing.getItemStyles().get(0).toString());
                listingIntent.putExtra("dateListed", listing.getDateTimeListed());
                listingIntent.putExtra("seller", listing.getSellerId());
                listingIntent.putExtra("imageFileName", listing.getItemImageFiles().get(0));


                /**
                 * TODO Allow for multiple materials, styles
                 * TODO modify price string
                 * 2 decimal places, or 0 decimal places if ends in 0
                 */

                startActivity(listingIntent);
            }
        });

        EditText searchInput = (EditText) findViewById(R.id.searchInput);
        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String text = searchInput.getText().toString();


                SearchQuery searchQuery;
                try {
                    Tokenizer tokenizer = new Tokenizer(text);
                    Parser parser = new Parser(tokenizer);
                    searchQuery = parser.parseQuery();
                } catch(Exception e) {
                    Toast.makeText(getApplicationContext(), "invalid characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                listings.clear();
                listings.addAll(searchQuery.search(allListings));
                listingArrayAdapter.notifyDataSetChanged();

            }
        });

        ImageButton imageButton2 = (ImageButton) findViewById(R.id.imageButton2);

        imageButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                searchInput.setText("");

                listings.clear();
                listings.addAll(allListings);
                listingArrayAdapter.notifyDataSetChanged();

            }
        });

    }
}