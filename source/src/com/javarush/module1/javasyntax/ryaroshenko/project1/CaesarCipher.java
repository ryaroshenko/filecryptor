package com.javarush.module1.javasyntax.ryaroshenko.project1;

import java.util.ArrayList;
import java.util.List;

/**
 * Клас шифрування/дешифрування потока символів алгоритмом Цезаря
 */
public class CaesarCipher {
    // Алфавіт шифрування
    private List<Character> alphabet;

    /**
     * Constructor
     */
    CaesarCipher() {
        alphabet = new ArrayList<>();
        fillAlphabet();
    }

    /**
     * Ініціалізація алфавіту
     */
    private void fillAlphabet() {
        if (alphabet == null)
            return;

        // Додаткові символи до алфавіту шифрування
        char[] symbols = new char[]{'.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '};

        alphabet.clear();

        for (char symbol = 'a'; symbol <= 'z'; symbol++)
            alphabet.add(symbol);

        for (char symbol = 'A'; symbol <= 'Z'; symbol++)
            alphabet.add(symbol);

        for (int index = 0; index < symbols.length; index++)
            alphabet.add(symbols[index]);
    }

    /**
     * Реалізація методу toString()
     *
     * @return String - увесь алфавіт у вигляді рядка
     */
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Character value : alphabet)
          result.append(value.charValue());
        return result.toString();
    }

    public List<Character> encrypt(List<Character> source, int key) {
        if ((source == null) || (key <= 0))
            return null;

        List<Character> result = new ArrayList<>();

        for (Character value : source)
            result.add(encryptSymbol(value.charValue(), key));

        return result;
    }

    public char encryptSymbol(char symbol, int key) {
        int index = alphabet.indexOf(symbol);

        if (index == -1)
            return symbol;

        char result = alphabet.get((index + key) % alphabet.size());

        return result;
    }
}
