package com.javarush.module1.javasyntax.ryaroshenko.project1;

import java.io.IOException;

public class Runner {
    public void execute(String[] args) throws IOException {
        if (args.length > 0) {
            new CommandLineMode(args, new ConsoleLog()).run();
        }
    }
}
