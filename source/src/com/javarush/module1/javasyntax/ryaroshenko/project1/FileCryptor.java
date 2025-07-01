package com.javarush.module1.javasyntax.ryaroshenko.project1;

import java.io.IOException;
import java.util.List;

/**
 * Основний клас шифратора/дешифратора файлів
 */
public class FileCryptor {
    public static void main(String[] args) throws IOException {
        if (args.length >= 3) {
            CryptType cryptType = CryptType.valueOf(args[0].toUpperCase());
            String filePath = args[1];
            int key = Integer.parseInt(args[2]);
            CaesarCipher cipher = new CaesarCipher();
            List<Character> cryptedList = cipher.crypt(FileService.readFile(filePath), key, cryptType);
            String fileSuffix = cryptType == CryptType.ENCRYPT ? "[ENCRYPTED]" : "[DECRYPTED]";
            FileService.writeFile(FileService.getFilePathWithSuffix(filePath, fileSuffix), cryptedList);
        }
    }
}
