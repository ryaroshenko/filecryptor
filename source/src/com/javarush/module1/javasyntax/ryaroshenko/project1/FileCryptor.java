package com.javarush.module1.javasyntax.ryaroshenko.project1;

import java.io.IOException;
import java.util.List;

/**
 * Основний клас шифратора/дешифратора файлів
 */
public class FileCryptor {
    public static void main(String[] args) throws IOException {
        if (args.length >= 2) {
            CryptType cryptType = CryptType.valueOf(args[0].toUpperCase());
            String filePath = args[1];
            int key;
            CaesarCipher cipher = new CaesarCipher();
            List<Character> sourceList = FileService.readFile(filePath);
            List<Character> cryptedList = null;
            String fileSuffix = "[]";
            switch (cryptType) {
                case ENCRYPT:
                case DECRYPT:
                    if (args.length >= 3) {
                        key = Integer.parseInt(args[2]);
                        cryptedList = cipher.crypt(sourceList, key, cryptType);
                        fileSuffix = cryptType == CryptType.ENCRYPT ? "[ENCRYPTED]" : "[DECRYPTED]";
                    }
                    break;
                case BRUTE_FORCE:
                    key = cipher.bruteForce(sourceList);
                    fileSuffix = "[key-" + key + "]";
                    if (key == 0)
                        cryptedList = sourceList;
                    else
                        cryptedList = cipher.decrypt(sourceList, key);
                    break;
            }
            FileService.writeFile(FileService.getFilePathWithSuffix(filePath, fileSuffix), cryptedList);
        }
    }
}
