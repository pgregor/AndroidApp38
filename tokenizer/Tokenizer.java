package com.example.androidproject.tokenizer;

import java.util.Scanner;

/**
 * @author Milton Dimos (u5849219) and COMP2100 team
 * Splits a string into small units called, 'Tokens', to be passed onto the Parser.
 */
public class Tokenizer {
    private String buffer;          // String to be transformed into tokens each time next() is called.
    private Token currentToken;     // The current token. The next token is extracted when next() is called.


    /**
     * The following exception should be thrown if the tokenizer is faced with token/s that do not
     * correlate with any of the defined accepted tokens.
     */
    public static class IllegalTokenException extends IllegalArgumentException {
        public IllegalTokenException(String errorMessage) {
            super(errorMessage);
        }
    }

    /**
     * Tokenizer class constructor
     * The constructor extracts the first token and saves it to currentToken
     */
    public Tokenizer(String text) {
        buffer = text;          // save input text (string)
        next();                 // extracts the first token.
    }

    /**
     * This function will find and extract a next token from {@code _buffer} and
     * save the token to {@code currentToken}.
     */
    public void next() {
        buffer = buffer.trim();     // remove whitespace

        if (buffer.isEmpty()) {
            currentToken = null;    // if there's no string left, set currentToken null and return
            return;
        }

        char firstChar = buffer.charAt(0);
        if (firstChar == ',') {
            currentToken = new Token(",", Token.Type.COM);
        } else if (firstChar == '<') {
            currentToken = new Token("<", Token.Type.LTHA);
        } else if (firstChar == '>') {
            currentToken = new Token(">", Token.Type.GTHA);
        } else if (firstChar == '#') {
            currentToken = new Token("#", Token.Type.HASH);
        } else if (firstChar == '|') {
            currentToken = new Token("|", Token.Type.BAR);
        } else if (Character.isAlphabetic(firstChar)) {
            int i = 0;
            while (buffer.length() > i && Character.isAlphabetic(buffer.charAt(i))) {
                i++;
            }
            String str = buffer.substring(0, i);

            currentToken = new Token(str, Token.Type.WORD);
        } else if (Character.isDigit(firstChar)) {
            int i = 0;
            while (buffer.length() > i && Character.isDigit(buffer.charAt(i))) {
                i++;
            }
            String num = buffer.substring(0, i);
            currentToken = new Token(num, Token.Type.INT);
        } else {
            throw new Token.IllegalTokenException("");
        }

        // Remove the extracted token from buffer
        int tokenLen = currentToken.getToken().length();
        buffer = buffer.substring(tokenLen);
    }

    /**
     * Returns the current token extracted by {@code next()}
     *
     * @return type: Token
     */
    public Token current() {
        return currentToken;
    }

    /**
     * Check whether tokenizer still has tokens left
     *
     * @return type: boolean
     */
    public boolean hasNext() {
        return currentToken != null;
    }
}
