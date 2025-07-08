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

    public void run() throws IOException {
        String error = validateArgs();
        if (error != null) {
            log.writeError(error);
            return;
        }
        parseArgs();
        List<Character> sourceList = readFile();
        if (sourceList == null)
            return;
        List<Character> cryptedList = crypt(sourceList);
        writeFile(cryptedList);
    }

    private String validateArgs() {
        if (args == null || args.length == 0)
            return Constants.NULL_PARAMS_ERROR;

        String cryptTypeStr = args[0];

        if (!(cryptTypeStr.equalsIgnoreCase(CryptType.ENCRYPT.toString()) ||
                cryptTypeStr.equalsIgnoreCase(CryptType.DECRYPT.toString()) ||
                cryptTypeStr.equalsIgnoreCase(CryptType.BRUTE_FORCE.toString())))
            return String.format(Constants.WRONG_CRYPT_TYPE_ERROR, cryptTypeStr);

        if ((cryptTypeStr.equalsIgnoreCase(CryptType.ENCRYPT.toString()) ||
                cryptTypeStr.equalsIgnoreCase(CryptType.DECRYPT.toString())) && args.length < 3)
            return Constants.WRONG_ENCRYPT_DECRYPT_PARAMS_ERROR;

        if (cryptTypeStr.equalsIgnoreCase(CryptType.BRUTE_FORCE.toString()) && args.length < 2)
            return Constants.WRONG_BRUTE_FORCE_PARAMS_ERROR;

        String filePath = args[1];
        if (Files.notExists(Path.of(filePath)))
            return String.format(Constants.FILE_NOT_EXISTS_ERROR, filePath);

        try {
            int key = Integer.parseInt(args[2]);
            if (key < 0)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            return String.format(Constants.KEY_FORMAT_EXCEPTION_ERROR, args[2]);
        }

        return null;
    }

    private void parseArgs() {
        cryptType = CryptType.valueOf(args[0].toUpperCase());
        filePath = args[1];
        if (args.length == 3)
            key = Integer.parseInt(args[2]);
    }

    private List<Character> readFile() {
        List<Character> sourceList = FileService.readFile(filePath);
        if (sourceList == null)
            log.writeError(Constants.FILE_READ_ERROR);
        else
            log.write(String.format(Constants.FILE_READ_COMPLETE, sourceList.size()));
        return sourceList;
    }

    private List<Character> crypt(List<Character> source) {
        CaesarCipher cipher = new CaesarCipher();
        List<Character> cryptedList = null;
        switch (cryptType) {
            case ENCRYPT:
                cryptedList = cipher.encrypt(source, key);
                fileSuffix = "[ENCRYPTED]";
                break;
            case DECRYPT:
                cryptedList = cipher.decrypt(source, key);
                fileSuffix = "[DECRYPTED]";
                break;
            case BRUTE_FORCE:
                key = cipher.bruteForce(source);
                cryptedList = cipher.decrypt(source, key);
                fileSuffix = "[key-" + key + "]";
                break;
        }
        return cryptedList;
    }

    private void writeFile(List<Character> source) throws IOException {
        FileService.writeFile(FileService.getFilePathWithSuffix(filePath, fileSuffix), source);
    }
}
