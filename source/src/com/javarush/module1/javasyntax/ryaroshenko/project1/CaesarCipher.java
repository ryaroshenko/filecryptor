package com.javarush.module1.javasyntax.ryaroshenko.project1;

/**
 * Клас шифрування/дешифрування потока символів алгоритмом Цезаря
 */
public class CaesarCipher {
    // Додаткові символи до алфавіту шифрування
    private char[] symbols = new char[]{'.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '};
    // Кількість літер англійського алфавіту в нижньому регістрі
    private int count_of_lower_symbols = ('z' - 'a') + 1;
    // Кількість літер англійського алфавіту в верхньому регістрі
    private int count_of_upper_symbols = ('Z' - 'A') + 1;
    // Алфавіт шифрування
    private char[] alphabet = new char[count_of_lower_symbols + count_of_upper_symbols + symbols.length];

    /**
     * Constructor
     */
    CaesarCipher() {
        fillAlphabet();
    }

    /**
     * Ініціалізація алфавіту
     */
    private void fillAlphabet() {
        int i = 0;

        for (char symbol = 'a'; symbol <= 'z'; symbol++) {
            alphabet[i] = symbol;
            i++;
        }

        for (char symbol = 'A'; symbol <= 'Z'; symbol++) {
            alphabet[i] = symbol;
            i++;
        }

        for (int index = 0; index < symbols.length; index++) {
            alphabet[i] = symbols[index];
            i++;
        }
    }

    /**
     * Реалізація методу toString()
     *
     * @return String - увесь алфавіт у вигляді рядка
     */
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(alphabet);
        return result.toString();
    }
}
