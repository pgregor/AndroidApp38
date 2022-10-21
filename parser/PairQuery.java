package com.example.androidproject.parser;

import java.util.ArrayList;

/**
 * @author @author Milton Dimos (u5849219)
 * Stores and evaluates two variables of type Query
 */
public class PairQuery extends Query {
	
	private Query item;
	private Query query;
	
	public PairQuery(Query item, Query query) {
		this.item = item;
		this.query = query;
	}

	@Override
	public String show() {
		return item.show() + " , " + query.show();
	}

	@Override
	public ArrayList<String> getQuery() {
		ArrayList<String> list = item.getQuery();
		list.addAll(query.getQuery());
		return list;
	}
}