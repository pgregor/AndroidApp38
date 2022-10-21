package com.example.androidproject.parser;

import com.example.androidproject.listing.Listing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 * @author @author Milton Dimos (u5849219)
 */
public class SortQuery {
    private final String type;
    private final String direction;

    public SortQuery(String type, String direction) {
        this.type = type;
        this.direction = direction;
    }

    public String show() {
        return "# " + type + "|" + direction;
    }

    public ArrayList<Listing> sort(ArrayList<Listing> list) {

        if (list.isEmpty()) {
            return new ArrayList<>();
        }

        Listing partition = list.get(list.size() - 1);
        ArrayList<Listing> low = new ArrayList<>();
        ArrayList<Listing> high = new ArrayList<>();

        for (int i = 0; i < list.size() - 1; i ++) {
            if (compare(partition,list.get(i)) > 0) {
                high.add(list.get(i));
            } else {
                low.add(list.get(i));
            }
        }

        ArrayList<Listing> sortedLow = sort(low);
        ArrayList<Listing> sortedHigh = sort(high);

        ArrayList<Listing> sortedList = new ArrayList<>();
        sortedList.addAll(sortedLow);
        sortedList.add(partition);
        sortedList.addAll(sortedHigh);

        return sortedList;
    }

    private Integer compare(Listing listing1, Listing listing2) {

        if (type.equals("price")) {
            double price1 = listing1.getPrice();
            double price2 = listing2.getPrice();
            if (direction.equals("lowest")) {
                if (price1 <= price2) {
                    return 1;
                } else {
                    return 0;
                }
            } else {
                if (price1 <= price2) {
                    return 0;
                } else {
                    return 1;
                }
            }
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date dateTime1;
            Date dateTime2;
            System.out.println(listing1.getDateTimeListed());
            System.out.println("listing2.getDateTimeListed()");
            try {
                dateTime1 = sdf.parse(listing1.getDateTimeListed());
                dateTime2 = sdf.parse(listing2.getDateTimeListed());
            } catch (Exception e) {
                System.out.println("hello");
                return 0;
            }

            if (direction.equals("newest")) {
                if (dateTime1.after(dateTime2)) {
                    return 1;
                } else {
                    return 0;
                }
            } else {
                if (dateTime1.after(dateTime2)) {
                    return 0;
                } else {
                    return 1;
                }
            }
        }
    }


}
