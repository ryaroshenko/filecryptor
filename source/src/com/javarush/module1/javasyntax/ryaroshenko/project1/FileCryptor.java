package com.javarush.module1.javasyntax.ryaroshenko.project1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Основний клас шифратора/дешифратора файлів
 */
public class FileCryptor {
    public static void main(String[] args) throws IOException {
        CaesarCipher cipher = new CaesarCipher();
        System.out.println(cipher);
        System.out.println(cipher.toString().length());
        System.out.println(cipher.encryptSymbol('c', 65));

        /*
        FileService file = new FileService();
        List<Character> list = file.readFile(".gitignore");
        System.out.println(list.size());
        Files.deleteIfExists(Path.of( ".gitignore_copy"));
        file.writeFile(".gitignore_copy", list);
         */
    }
}
