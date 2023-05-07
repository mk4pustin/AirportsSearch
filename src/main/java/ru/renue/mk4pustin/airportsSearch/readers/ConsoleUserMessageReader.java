package ru.renue.mk4pustin.airportsSearch.readers;

import java.util.Scanner;

public class ConsoleUserMessageReader implements UserMessageReader {
    private final Scanner CONSOLE = new Scanner(System.in);

    @Override
    public String read() {
        return CONSOLE.nextLine();
    }
}
