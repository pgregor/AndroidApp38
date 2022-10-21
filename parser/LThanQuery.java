package com.example.androidproject.parser;

import com.example.androidproject.listing.Listing;

import java.util.ArrayList;

/**
 * @author @author Milton Dimos (u5849219)
 */
public class LThanQuery extends SearchQuery {
	
	private Integer value;

	public LThanQuery(Integer value) {
		this.value = value;
	}

	@Override
	public String show() {
		return "< " + value.toString();
	}

	@Override
	public ArrayList<Listing> search(ArrayList<Listing> list) {
		ArrayList<Listing> newList = new ArrayList<Listing>();
		double num;
		for (Listing item : list) {
			num = item.getPrice();
			if (num < this.value) {
				newList.add(item);
			}
		}
		return newList;
	}
}
