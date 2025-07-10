package com.javarush.module1.javasyntax.ryaroshenko.project1;

import java.io.IOException;

public class Runner {
    public void execute(String[] args) throws IOException, WrongArgsException {
        ProgramMode program;

        if (args.length > 0)
            program = new CommandLineMode(args, new ConsoleLog());
        else
            program = new ConsoleMode(new ConsoleLog());

        program.run();
    }
}
