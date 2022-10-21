package com.example.androidproject.parser;

import com.example.androidproject.tokenizer.Token;
import com.example.androidproject.tokenizer.Tokenizer;

/**
 * @author Milton Dimos (u5849219) and COMP2100 team
 * Takes a bunch of tokens and returns an expression which can be evaluated.
 */
public class Parser {
    /**
     * The following exception should be thrown if the parser is faced with series of tokens that do not
     * correlate with any possible production rule.
     */
    public static class IllegalProductionException extends IllegalArgumentException {
        public IllegalProductionException(String errorMessage) {
            super(errorMessage);
        }
    }

    // The tokenizer (class field) this parser will use.
    Tokenizer tokenizer;

    /**
     * Parser class constructor
     * Simply sets the tokenizer field.
     */
    public Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    /**
     * Adheres to the grammar rule:
     * <query>    ::= zero to many <wordQuery> no more than one <priceComp> no more than one <sortQuery>
     *              listed in any order, comma separated
     *
     * @return type: Query.
     */
    public SearchQuery parseQuery() {

        if (tokenizer.current() == null) {
            return new MixedQuery(null,null, null);
        }
        Query wordQuery = null;
        SortQuery sortQuery = null;
        SearchQuery priceComp = null;
        while (tokenizer.hasNext()) {
            if (tokenizer.current().getType() == Token.Type.GTHA ||
                tokenizer.current().getType() == Token.Type.LTHA ||
                tokenizer.current().getType() == Token.Type.INT) {
                if (priceComp != null) {
                    tokenizer.next();
                } else {
                    priceComp = parsePriceComp();
                }
            } else if (tokenizer.current().getType() == Token.Type.WORD) {
                if (wordQuery == null) {
                    wordQuery = parseWordQuery();
                } else {
                    Query newQuery;
                    newQuery = wordQuery;
                    wordQuery = new PairQuery(newQuery, parseWordQuery());
                }
            } else if (tokenizer.current().getType() == Token.Type.HASH) {
                if (sortQuery == null) {
                    sortQuery = parseSortQuery();
                } else {
                    parseSortQuery();
                }
            } else {
                tokenizer.next();
            }
        }
        return new MixedQuery(wordQuery, sortQuery, priceComp);

    }

    /**
     * Adheres to the grammar rule:
     * <wordQuery>   ::=  <Word> | <Word> , <wordQuery>
     *
     * @return type: Query.
     */
    public Query parseWordQuery() {

        String word = tokenizer.current().getToken();
        Query wordQuery = new WordQuery(word);
        tokenizer.next();

        while (tokenizer.hasNext() && tokenizer.current().getType() == Token.Type.COM) {
            tokenizer.next();
        }

        if (!tokenizer.hasNext()) {
            return wordQuery;
        }

        if (tokenizer.current().getType() == Token.Type.WORD) {
            Query query = parseWordQuery();
            return new PairQuery(wordQuery, query);
        } else {
            return wordQuery;
        }

    }

    /**
     * Adheres to the grammar rule:
     * <priceComp>   ::=  > <Int> | < <Int>
     *
     * @return type: Query.
     */
    public SearchQuery parsePriceComp() {

        SearchQuery priceComp;
        if (tokenizer.current().getType() == Token.Type.LTHA) {
            tokenizer.next();
            if (!tokenizer.hasNext() || tokenizer.current().getType() != Token.Type.INT) {
                return null;
            }
            String num = tokenizer.current().getToken();
            priceComp = new LThanQuery(Integer.parseInt(num));
        } else if (tokenizer.current().getType() == Token.Type.GTHA) {
            tokenizer.next();
            if (!tokenizer.hasNext() || tokenizer.current().getType() != Token.Type.INT) {
                return null;
            }
            String num = tokenizer.current().getToken();
            priceComp = new GThanQuery(Integer.parseInt(num));
        } else {
            String num = tokenizer.current().getToken();
            tokenizer.next();
            if (!tokenizer.hasNext()) {
                return null;
            }
            if (tokenizer.current().getType() == Token.Type.LTHA) {
                priceComp = new GThanQuery(Integer.parseInt(num));
            } else if (tokenizer.current().getType() == Token.Type.GTHA) {
                priceComp = new LThanQuery(Integer.parseInt(num));
            } else {
                return null;
            }
        }

        tokenizer.next();
        return priceComp;
    }

    /**
     * Adheres to the grammar rule:
     * <sortQuery>      ::=  # <type|direction> | # <type>
     * <Type|direction> ::=  price|lowest | price|highest | date|newest | date|oldest
     * <Type>           ::=  price | date
     *
     * @return type: Query.
     */
    public SortQuery parseSortQuery() {

        SortQuery sortQuery;
        tokenizer.next();

        if (tokenizer.current().getType() == Token.Type.WORD) {
            String type = tokenizer.current().getToken();
            type.toLowerCase();
            tokenizer.next();
            String direction = null;
            if (tokenizer.current().getType() == Token.Type.BAR) {
                tokenizer.next();
                if (tokenizer.current().getType() == Token.Type.WORD) {
                    direction = tokenizer.current().getToken();
                    direction.toLowerCase();
                    tokenizer.next();
                }
            }
            if (type.equals("price")) {
                if (direction == null) {
                    sortQuery = new SortQuery(type, "lowest");
                } else if (direction.equals("lowest") || direction.equals("highest")) {
                    sortQuery = new SortQuery(type, direction);
                } else {
                    return null;
                }
            } else if (type.equals("date")) {
                if (direction == null) {
                    sortQuery = new SortQuery(type, "newest");
                } else if (direction.equals("newest") || direction.equals("oldest")) {
                    sortQuery = new SortQuery(type, direction);
                } else {
                    return null;
                }
            } else {
                return null;
            }
            return sortQuery;
        }

        return null;
    }

}

