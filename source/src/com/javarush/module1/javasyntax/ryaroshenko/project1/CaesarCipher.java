package com.javarush.module1.javasyntax.ryaroshenko.project1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Клас шифрування/дешифрування потока символів алгоритмом Цезаря
 */
public class CaesarCipher {
    // Алфавіт шифрування
    private List<Character> alphabet;
    // Рядки для перевірки алгоритму brute-force
    private final String[] CHECK_LIST = new String[]{", ", " and ", " the ", " - ", " they ", " or ", ": ",
        " і ", " та ", " або ", " він ", " вона ", " воно ", " хіба ", " має ", " є ", " її ", " його ", " ти ",
        " яке ", " яка ", " який ", " для ", " що ", " чим ", " she ", " he ", " have ", " has ", " to ", " was "};

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
        // Український алфавіт
        char[] alphabetUAUpper = new char[]{'А', 'Б', 'В', 'Г', 'Ґ', 'Д', 'Е', 'Є', 'Ж', 'З', 'И', 'І', 'Ї', 'Й', 'К',
                'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ь', 'Ю', 'Я'};
        char[] alphabetUALower = new char[]{'а', 'б', 'в', 'г', 'ґ', 'д', 'е', 'є', 'ж', 'з', 'и', 'і', 'ї', 'й', 'к',
                'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ь', 'ю', 'я'};
        // Додаткові символи до алфавіту шифрування
        char[] symbols = new char[]{'.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '};
        alphabet.clear();
        for (char symbol = 'A'; symbol <= 'Z'; symbol++)
            alphabet.add(symbol);
        for (char symbol = 'a'; symbol <= 'z'; symbol++)
            alphabet.add(symbol);
        addAlphabet(alphabetUAUpper);
        addAlphabet(alphabetUALower);
        addAlphabet(symbols);
    }

    /**
     * addAlphabet - додавання символів до алфавіту
     *
     * @param symbols - масив символів
     */
    private void addAlphabet(char[] symbols) {
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

    /**
     * crypt - шифрування або дешифрування списку символів
     *
     * @param source    - вхідний список символів
     * @param key       - ключ шифрування
     * @param cryptType - тип шифрування
     * @return вихідний список символів
     */
    private List<Character> crypt(List<Character> source, int key, CryptType cryptType) {
        if ((source == null) || (key < 0))
            return null;
        if (key % alphabet.size() == 0)
            return source;
        List<Character> result = new ArrayList<>();
        for (Character value : source) {
            switch (cryptType) {
                case ENCRYPT:
                    result.add(encryptSymbol(value, key));
                    break;
                case DECRYPT:
                    result.add(decryptSymbol(value, key));
                    break;
            }
        }
        return result;
    }

    /**
     * encrypt - шифрування списку символів
     *
     * @param source - вхідний незашифрований список символів
     * @param key    - ключ шифрування
     * @return вихідний зашифрований список символів
     */
    public List<Character> encrypt(List<Character> source, int key) {
        return crypt(source, key, CryptType.ENCRYPT);
    }

    /**
     * encryptSymbol - шифрування одного символа
     *
     * @param symbol - вхідний незашифрований символ
     * @param key    - ключ шифрування
     * @return вихідний зашифрований символ
     */
    public char encryptSymbol(char symbol, int key) {
        int index = alphabet.indexOf(symbol);
        if (index == -1)
            return symbol;
        else
            return alphabet.get((index + key) % alphabet.size());
    }

    /**
     * decrypt - дешифрування списку символів
     *
     * @param source - вхідний зашифрований список символів
     * @param key    - ключ шифрування
     * @return вихідний дешифрований список символів
     */
    public List<Character> decrypt(List<Character> source, int key) {
        return crypt(source, key, CryptType.DECRYPT);
    }

    /**
     * decryptSymbol - дешифрування одного символа
     *
     * @param symbol - вхідний зашифрований символ
     * @param key    - ключ шифрування
     * @return вихідний дешифрований символ
     */
    public char decryptSymbol(char symbol, int key) {
        int index = alphabet.indexOf(symbol);
        if (index == -1)
            return symbol;
        else {
            int pos = index - key % alphabet.size();
            if (pos < 0)
                pos += alphabet.size();
            return alphabet.get(pos);
        }
    }

    /**
     * bruteForce - основна функція підбору ключа методом brute-force
     *
     * @param source - вхідний зашифрований список символів
     * @return знайдений ключ
     */
    public int bruteForce(List<Character> source) {
        int key;
        Map<Integer, Integer> map = new HashMap<>();
        for (key = 1; key < alphabet.size(); key++) {
            List<Character> list = decrypt(source, key);
            map.put(key, checkSum(list));
        }
        int max = Integer.MIN_VALUE;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                key = entry.getKey();
            }
        }
        if (max <= 0)
            key = 0;
        return key;
    }

    /**
     * checkWord - функція визначення наявності потрібного слова починаючи з заданої позиції
     *
     * @param source    - вхідний список символів
     * @param indexFrom - індекс початку визначення
     * @param word      - слово, яке очікуємо бачити
     * @return індекс, з якого треба продовжити пошук або -1 - якщо слова не буде
     */
    private int checkWord(List<Character> source, int indexFrom, String word) {
        int indexTo = indexFrom;
        for (int i = 0; i < word.length(); i++) {
            if ((indexTo < source.size()) && (word.charAt(i) == source.get(indexTo)))
                indexTo++;
            else {
                indexTo = -1;
                break;
            }
        }
        return indexTo;
    }

    /**
     * countWord - підрахунок скільки разів зустрічається потрібне слово
     *
     * @param source - вхідний список символів
     * @param word   - слово, яке підраховуємо
     * @return кількість "зустрічей"
     */
    private int countWord(List<Character> source, String word) {
        int count = 0;
        int index = 0;
        while (index < source.size()) {
            int indexTo = checkWord(source, index, word);
            if (indexTo == -1)
                index++;
            else {
                count++;
                index = indexTo;
            }
        }
        return count;
    }

    /**
     * checkSum - підрахунок контрольної суми
     *
     * @param source - вхідний список символів
     * @return значення контрольної суми
     */
    private int checkSum(List<Character> source) {
        int sum = 0;
        for (int i = 0; i < CHECK_LIST.length; i++)
            sum += countWord(source, CHECK_LIST[i]);
        return sum;
    }
}
