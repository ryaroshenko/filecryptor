package com.javarush.module1.javasyntax.ryaroshenko.project1;

public class consoleLog implements Log {
    public void writeln(String str) {
        System.out.println(str);
    }
}
