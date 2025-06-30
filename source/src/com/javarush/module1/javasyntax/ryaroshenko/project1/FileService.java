package com.javarush.module1.javasyntax.ryaroshenko.project1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Клас для роботи з файлами
 */
public class FileService {
    /**
     * readFile - завантаження змісту файлу в List<Character>
     *
     * @param filePath - назва файлу разом з його шляхом
     * @return List<Character> - колекція символів з файлу
     */
    public List<Character> readFile(String filePath) {
        List<Character> list = new ArrayList<>();

        try {
            BufferedReader buffer = new BufferedReader(new FileReader(filePath));
            int value;

            while ((value = buffer.read()) != -1)
                list.add((char) value);

            buffer.close();
        } catch (IOException e) {
            return null;
        }

        return list;
    }

    /**
     * writeFile - збереження змісту List<Character>
     *
     * @param filePath - назва файлу разом з його шляхом
     * @param list     - колекція символів для файлу
     */
    public void writeFile(String filePath, List<Character> list) {
        if (list == null)
            return;

        try {
            BufferedWriter buffer = new BufferedWriter(new FileWriter(filePath));

            for (Character value : list)
                buffer.write(value.charValue());

            buffer.close();
        } catch (IOException e) {
        }
    }
}
