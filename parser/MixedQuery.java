package com.example.androidproject.parser;

import com.example.androidproject.listing.Listing;

import java.util.ArrayList;

/**
 * @author @author Milton Dimos (u5849219)
 */
public class MixedQuery extends SearchQuery {

    private Query wordQuery;
    private SortQuery sortQuery;
    private SearchQuery priceComp;

    public MixedQuery(Query wordQuery , SortQuery sortQuery, SearchQuery priceComp) {
        this.priceComp = priceComp;
        this.sortQuery = sortQuery;
        this.wordQuery = wordQuery;
    }

    @Override
    public String show() {
        String show = "";
        if (wordQuery != null) {
            show += wordQuery.show();
            if (priceComp != null || sortQuery != null) {
                show += " , ";
            }
        }
        if (priceComp != null) {
            show += priceComp.show();
            if (sortQuery != null) {
                show += " , ";
            }
        }
        if (sortQuery != null) {
            show += sortQuery.show();
        }
        return show;
    }

    @Override
    public ArrayList<Listing> search(ArrayList<Listing> list) {
        if (wordQuery == null && sortQuery == null && priceComp == null) {
            return new ArrayList<>(list);
        }

        ArrayList<String> wordList = new ArrayList<>();
        if (wordQuery != null) {
            wordList.addAll(wordQuery.getQuery());
        }

        ArrayList<Listing> priceList = new ArrayList<>();

        if (priceComp != null) {
            priceList.addAll(priceComp.search(list));
        } else {
            priceList.addAll(list);
        }

        ArrayList<Listing> searchList = new ArrayList<>();

        if (!wordList.isEmpty()) {
            String name;
            String desc;
            boolean valid;
            for (Listing item : priceList) {
                name = item.getItemName().toLowerCase();
                desc = item.getItemDescription().toLowerCase();
                valid = false;
                for (String word : wordList) {
                    if (name.contains(word) || desc.contains(word)) {
                        valid = true;
                        break;
                    }
                }
                if (valid) {
                    searchList.add(item);
                }
            }
        } else {
            searchList.addAll(priceList);
        }


        if (sortQuery == null) {
            return searchList;
        }

        ArrayList<Listing> sortedList = new ArrayList<>();

        sortedList.addAll(sortQuery.sort(searchList));

        return sortedList;
    }

}
