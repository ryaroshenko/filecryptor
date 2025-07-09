package com.javarush.module1.javasyntax.ryaroshenko.project1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CommandLineMode {
    private String[] args;
    private Log log;
    private CryptType cryptType;
    private String filePath;
    private int key = 0;
    private String fileSuffix = "[]";

    public CommandLineMode(String[] args, Log log) {
        this.args = args;
        this.log = log;
    }

    public CommandLineMode(String[] args) {
        this(args, new NullLog());
    }

    public void run() throws IOException, WrongArgsException {
        try {
            validateArgs();
            parseArgs();
            writeFile(crypt(readFile()));
        } catch (IOException | WrongArgsException e) {
            log.writeError(e.getMessage());
        }
    }

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

    private void parseArgs() {
        cryptType = CryptType.valueOf(args[0].toUpperCase());
        filePath = args[1];
        if (args.length == 3)
            key = Integer.parseInt(args[2]);
    }

    private List<Character> readFile() throws IOException {
        List<Character> sourceList = FileService.readFile(filePath);
        if (sourceList == null || sourceList.size() == 0)
            throw new IOException(Constants.SOURCE_LIST_EMPTY_ERROR);
        else
            log.write(String.format(Constants.FILE_READ_COMPLETE, sourceList.size(), filePath));
        return sourceList;
    }

    private List<Character> crypt(List<Character> source) {
        CaesarCipher cipher = new CaesarCipher();
        List<Character> cryptedList = null;
        switch (cryptType) {
            case ENCRYPT:
                cryptedList = cipher.encrypt(source, key);
                log.write(String.format(Constants.ENCRYPT_COMPLETE, cryptedList.size()));
                fileSuffix = "[ENCRYPTED]";
                break;
            case DECRYPT:
                cryptedList = cipher.decrypt(source, key);
                log.write(String.format(Constants.DECRYPT_COMPLETE, cryptedList.size()));
                fileSuffix = "[DECRYPTED]";
                break;
            case BRUTE_FORCE:
                log.write(Constants.VALUE_OF_KEY_SEARCHING);
                key = cipher.bruteForce(source);
                log.write(String.format(Constants.VALUE_OF_KEY_FOUND, key));
                cryptedList = cipher.decrypt(source, key);
                log.write(String.format(Constants.DECRYPT_COMPLETE, cryptedList.size()));
                fileSuffix = "[key-" + key + "]";
                break;
        }
        return cryptedList;
    }

    private void writeFile(List<Character> source) throws IOException {
        String newFilePath = FileService.getFilePathWithSuffix(filePath, fileSuffix);
        FileService.writeFile(newFilePath, source);
        log.write(String.format(Constants.FILE_WRITE_COMPLETE, source.size(), newFilePath));
    }
}
