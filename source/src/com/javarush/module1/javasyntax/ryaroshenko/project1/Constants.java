package com.javarush.module1.javasyntax.ryaroshenko.project1;

public interface Constants {
    String NULL_PARAMS_ERROR = "Масив параметрів командного рядка пустий";
    String WRONG_CRYPT_TYPE_ERROR = "\"%s\" - неправильне значення команди!\n  має бути:\n   ENCRYPT - шифрування\n   DECRYPT - дешифрування\n   BRUTE_FORCE - дешифрування з підбором ключа";
    String FILE_NOT_EXISTS_ERROR = "\"%s\" - заданий файл не існує";
    String WRONG_ENCRYPT_DECRYPT_PARAMS_ERROR = "Для команд ENCRYPT та DECRYPT формат параметрів має бути таким: \"command filePath key\"";
    String WRONG_BRUTE_FORCE_PARAMS_ERROR = "Для команди BRUTE_FORCE формат параметрів має бути таким: \"command filePath\"";
    String KEY_FORMAT_EXCEPTION_ERROR = "\"%s\" - неправильний формат ключа, ключ має бути більше або дорівнювати нулю";
    String FILE_READ_COMPLETE = "Зчитано %d символів з файлу \"%s\"";
    String SOURCE_LIST_EMPTY_ERROR = "Масив для операцій з файлом пустий";
    String FILE_WRITE_COMPLETE = "Записано %d символів до файлу \"%s\"";
    String ENCRYPT_COMPLETE = "Зашифровано %d символів";
    String DECRYPT_COMPLETE = "Дешифровано %d символів";
    String VALUE_OF_KEY_SEARCHING = "Шукаю ключа ...";
    String VALUE_OF_KEY_FOUND = "Знайдено значення ключа: %d";
}
