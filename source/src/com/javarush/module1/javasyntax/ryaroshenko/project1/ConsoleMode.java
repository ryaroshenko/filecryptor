package com.javarush.module1.javasyntax.ryaroshenko.project1;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class ConsoleMode extends ProgramMode {
    private Scanner scan;

    /**
     * Коструктор
     *
     * @param log - об'єкт логування
     */
    public ConsoleMode(Log log) {
        this.log = log;
        this.scan = new Scanner(System.in);
    }

    /**
     * Коструктор
     */
    public ConsoleMode() {
        this(new NullLog());
    }

    protected boolean prepare() throws WrongArgsException {
        boolean next = false;
        int menuIndex = -1;
        while (!next) {
            menu();
            menuIndex = readInt("Ваш вибір");
            switch (menuIndex) {
                case 1:
                    cryptType = CryptType.ENCRYPT;
                    break;
                case 2:
                    cryptType = CryptType.DECRYPT;
                    break;
                case 3:
                    cryptType = CryptType.BRUTE_FORCE;
                    break;
            }
            next = execMenu(menuIndex);
        }
        return menuIndex != 4;
    }

    private void menu() {
        System.out.println();
        System.out.println("Виберіть потрібний режим роботи:");
        System.out.println("  1. Шифрування");
        System.out.println("  2. Дешифрування");
        System.out.println("  3. Підбір ключа методом brute-force");
        System.out.println("  4. Вихід");
    }

    private String readLine(String caption) {
        System.out.print(caption + ": ");
        String value = scan.nextLine();
        while (value.isEmpty())
            value = scan.nextLine();
        return value;
    }

    private int readInt(String caption) {
        System.out.print(caption + ": ");
        if (scan.hasNextInt())
            return scan.nextInt();
        else
            return -1;
    }

    private boolean execMenu(int menuIndex) {
        if (menuIndex >= 1 && menuIndex <= 3) {
            boolean repeat = true;
            while (repeat) {
                filePath = readLine("Введіть шлях до файлу");
                if (filePath.equalsIgnoreCase("quit"))
                    System.exit(0);
                repeat = Files.notExists(Path.of(filePath));
                if (repeat)
                    log.write(String.format(Constants.FILE_NOT_EXISTS_ERROR, filePath));
            }
            if (menuIndex == 1 || menuIndex == 2) {
                key = -1;
                while (key < 0) {
                    String value = readLine("Введіть значення ключа");
                    if (value.equalsIgnoreCase("quit"))
                        System.exit(0);
                    try {
                        key = Integer.parseInt(value);
                        if (key < 0)
                            throw new NumberFormatException();
                    } catch (NumberFormatException e) {
                        log.write(String.format(Constants.KEY_FORMAT_EXCEPTION_ERROR, value));
                    }
                }
            }
            return true;
        } else if (menuIndex == 4) {
            System.exit(0);
        } else if (menuIndex < 1 || menuIndex > 4) {
            log.write("Треба вводити числа від 1 до 4");
        }
        return false;
    }
}
