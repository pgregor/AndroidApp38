package com.example.androidproject.search;

import android.content.Context;
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
import com.example.androidproject.listing.Item;
import com.example.androidproject.listing.Listing;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SearchResultAdapter extends ArrayAdapter<Listing> {
    //private ArrayList<Listing> listingsArray;
    //private Context context;

    public SearchResultAdapter(@NonNull Context context, int resource, ArrayList<Listing> listingsArray) {
        super(context, resource, listingsArray);
        //this.listingsArray = listingsArray;
        //this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listing, parent, false);

            /**
             * Get UI elements
             */
            TextView nameListingInfo = (TextView) convertView.findViewById(R.id.nameListingInfo);
            TextView priceListingInfo = (TextView) convertView.findViewById(R.id.priceListingInfo);
            ImageView imageListingInfo = (ImageView) convertView.findViewById(R.id.imageListingInfo);

            /**
             * Set properties of UI elements from listingsArray
             */

            Listing listing = super.getItem(position);
//            Item item = new Item().getItemFromListingId(getContext(),listing.getId());
            nameListingInfo.setText(listing.getItemName());
            priceListingInfo.setText("Price: $" + listing.getPrice());
                    try{
                        InputStream is = super.getContext().getAssets().open("listing_images/" + listing.getItemImageFiles().get(0));
                        Drawable d = Drawable.createFromStream(is, null);
                        imageListingInfo.setImageDrawable(d);
                    }
                    catch(IOException e) {
                        e.printStackTrace();
                    }
        }
        return convertView;
    }

}
