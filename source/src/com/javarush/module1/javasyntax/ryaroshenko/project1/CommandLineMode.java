package com.javarush.module1.javasyntax.ryaroshenko.project1;

import java.nio.file.Files;
import java.nio.file.Path;

public class CommandLineMode extends ProgramMode {
    // Копія аргументів командного рядка
    private String[] args;

    /**
     * Коструктор
     *
     * @param args - агрументи командного рядка
     * @param log  - об'єкт логування
     */
    public CommandLineMode(String[] args, Log log) {
        this.args = args;
        this.log = log;
    }

    /**
     * Конструктор
     *
     * @param args - агрументи командного рядка
     */
    public CommandLineMode(String[] args) {
        this(args, new NullLog());
    }

    protected boolean prepare() throws WrongArgsException {
        validateArgs();
        parseArgs();
        return false;
    }

    /**
     * validateArgs - метод валідації аргументів командного рядка
     *
     * @throws WrongArgsException
     */
    private void validateArgs() throws WrongArgsException {
        if (args == null || args.length == 0)
            throw new WrongArgsException(Constants.NULL_PARAMS_ERROR);

        String cryptTypeStr = args[0];

        if (!(cryptTypeStr.equalsIgnoreCase(CryptType.ENCRYPT.toString()) ||
                cryptTypeStr.equalsIgnoreCase(CryptType.DECRYPT.toString()) ||
                cryptTypeStr.equalsIgnoreCase(CryptType.BRUTE_FORCE.toString())))
            throw new WrongArgsException(String.format(Constants.WRONG_CRYPT_TYPE_ERROR, cryptTypeStr));

        if (cryptTypeStr.equalsIgnoreCase(CryptType.ENCRYPT.toString()) || cryptTypeStr.equalsIgnoreCase(CryptType.DECRYPT.toString())) {
            if (args.length != 3)
                throw new WrongArgsException(Constants.WRONG_ENCRYPT_DECRYPT_PARAMS_ERROR);

            try {
                int key = Integer.parseInt(args[2]);
                if (key < 0)
                    throw new NumberFormatException();
            } catch (NumberFormatException e) {
                throw new WrongArgsException(String.format(Constants.KEY_FORMAT_EXCEPTION_ERROR, args[2]));
            }
        }

        if (cryptTypeStr.equalsIgnoreCase(CryptType.BRUTE_FORCE.toString()) && args.length != 2)
            throw new WrongArgsException(Constants.WRONG_BRUTE_FORCE_PARAMS_ERROR);

        String filePath = args[1];
        if (Files.notExists(Path.of(filePath)))
            throw new WrongArgsException(String.format(Constants.FILE_NOT_EXISTS_ERROR, filePath));
    }

    /**
     * parseArgs - парсінг аргументів командного рядка
     */
    private void parseArgs() {
        cryptType = CryptType.valueOf(args[0].toUpperCase());
        filePath = args[1];
        if (args.length == 3)
            key = Integer.parseInt(args[2]);
    }
}
