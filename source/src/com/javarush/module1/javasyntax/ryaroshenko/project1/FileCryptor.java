package com.javarush.module1.javasyntax.ryaroshenko.project1;

/**
 * Основний клас шифратора/дешифратора файлів
 */
public class FileCryptor {
    public static void main(String[] args) {
        CaesarCipher cipher = new CaesarCipher();
        System.out.println(cipher);
    }
}
