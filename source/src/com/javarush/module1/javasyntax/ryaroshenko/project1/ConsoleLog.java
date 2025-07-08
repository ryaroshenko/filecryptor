package com.javarush.module1.javasyntax.ryaroshenko.project1;

public class ConsoleLog implements Log {
    public void write(String str) {
        System.out.println(str);
    }

    public void writeError(String str) {
        System.err.println(str);
    }
}
