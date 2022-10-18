package org.example;

import java.util.ArrayList;
import java.util.List;

public class VigenereEncryptor {
    private static final List<Character> alphabet;
    private static final String secretWord = "SYST";
    private int spaceCounter = 0;

    static {
        alphabet = new ArrayList<>();
        for (char l = 'A'; l <= 'Z'; l++) {
            alphabet.add(l);
        }
    }

    public String encrypt(String text) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            stringBuilder.append(encryptLetter(text.charAt(i), i));
        }
        return stringBuilder.toString();
    }


    private char encryptLetter(char letter, int i) {
        if (letter != ' ') {
            int shift = alphabet.indexOf(secretWord.charAt((i - spaceCounter) % secretWord.length()));
            return alphabet.get((alphabet.indexOf(letter) + shift) % alphabet.size());
        } else spaceCounter++;
        return ' ';
    }
}
