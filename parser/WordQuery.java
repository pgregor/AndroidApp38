package com.example.androidproject.parser;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author @author Milton Dimos (u5849219)
 */
public class WordQuery extends Query {

	private String word;

	public WordQuery(String word) {
		this.word = word;
	}

	@Override
	public String show() {
		return word;
	}

	@Override
	public ArrayList<String> getQuery() {
		return new ArrayList<>(Arrays.asList(word.toLowerCase()));
	}
}
