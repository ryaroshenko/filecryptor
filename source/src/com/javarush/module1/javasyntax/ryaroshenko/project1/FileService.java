package com.javarush.module1.javasyntax.ryaroshenko.project1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileService {
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
}
