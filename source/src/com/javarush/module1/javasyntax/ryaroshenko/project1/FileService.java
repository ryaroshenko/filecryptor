package com.javarush.module1.javasyntax.ryaroshenko.project1;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
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
    public static List<Character> readFile(String filePath) throws IOException {
        List<Character> list = new ArrayList<>();
        BufferedReader buffer = new BufferedReader(new FileReader(filePath));
        int value;
        while ((value = buffer.read()) != -1)
            list.add((char) value);
        buffer.close();
        return list;
    }

    /**
     * writeFile - збереження змісту List<Character>
     *
     * @param filePath - назва файлу разом з його шляхом
     * @param list     - колекція символів для файлу
     */
    public static void writeFile(String filePath, List<Character> list) throws IOException {
        if (list == null || list.size() == 0)
            throw new IOException(Constants.SOURCE_LIST_EMPTY_ERROR);
        Files.deleteIfExists(Path.of(filePath));
        BufferedWriter buffer = new BufferedWriter(new FileWriter(filePath));
        for (Character value : list)
            buffer.write(value);
        buffer.close();
    }

    /**
     * getFileExt - отримання розширення файлу
     *
     * @param filePath - шлях до файлу
     * @return розширення файлу
     */
    public static String getFileExt(String filePath) {
        String fileName = Path.of(filePath).getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex == fileName.length() - 1)
            return "";
        else
            return fileName.substring(dotIndex);
    }

    /**
     * getFilePathWithSuffix - вставка суфіксу між назвою та розширенням файлу
     *
     * @param filePath - шлях до файлу
     * @param suffix   - значення суфіксу
     * @return шлях до файлу разом з суфіксом, що вставили
     */
    public static String getFilePathWithSuffix(String filePath, String suffix) {
        String fileExt = getFileExt(filePath);
        return filePath.replaceFirst(fileExt, "") + suffix + fileExt;
    }
}
