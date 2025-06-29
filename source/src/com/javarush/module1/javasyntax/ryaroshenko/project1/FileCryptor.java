package com.javarush.module1.javasyntax.ryaroshenko.project1;

import java.util.List;

/**
 * Основний клас шифратора/дешифратора файлів
 */
public class FileCryptor {
    public static void main(String[] args) {
        CaesarCipher cipher = new CaesarCipher();
        System.out.println(cipher);

        FileService file = new FileService();
        List<Character> list = file.readFile(".gitignore");
        System.out.println(list.size());
    }
}
