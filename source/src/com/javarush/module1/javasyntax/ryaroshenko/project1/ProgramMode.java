package com.javarush.module1.javasyntax.ryaroshenko.project1;

import java.io.IOException;
import java.util.List;

public class ProgramMode {
    protected Log log;
    protected CryptType cryptType;
    protected String filePath;
    protected int key = 0;
    protected String fileSuffix = "[]";

    /**
     * run - основний метод для запуску програми
     */
    public void run() {
        try {
            boolean repeat = true;
            while (repeat) {
                repeat = prepare();
                writeFile(crypt(readFile()));
            }
        } catch (IOException | WrongArgsException e) {
            log.writeError(e.getMessage());
        }
    }

    protected boolean prepare() throws WrongArgsException {
        return false;
    }

    /**
     * readFile - вичитка файлу
     *
     * @return - масив символів
     * @throws IOException
     */
    private List<Character> readFile() throws IOException {
        List<Character> sourceList = FileService.readFile(filePath);
        if (sourceList == null || sourceList.isEmpty())
            throw new IOException(Constants.SOURCE_LIST_EMPTY_ERROR);
        else
            log.write(String.format(Constants.FILE_READ_COMPLETE, sourceList.size(), filePath));
        return sourceList;
    }

    /**
     * writeFile - запис масиву символів в файл
     *
     * @param source - масив символів
     * @throws IOException
     */
    private void writeFile(List<Character> source) throws IOException {
        String newFilePath = FileService.getFilePathWithSuffix(filePath, fileSuffix);
        FileService.writeFile(newFilePath, source);
        log.write(String.format(Constants.FILE_WRITE_COMPLETE, source.size(), newFilePath));
    }

    /**
     * crypt - запуск методів шифрування в залежності від режиму
     *
     * @param source - масив символів
     * @return
     */
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
}
