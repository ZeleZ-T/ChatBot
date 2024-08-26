package co.zelez;

import co.zelez.core.chat.usecase.ConsoleManager;
import co.zelez.core.command.reader.entity.Param;
import co.zelez.core.command.reader.usecase.ReaderService;

import java.io.Console;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ConsoleManager manager = new ConsoleManager();
        manager.chat();
        System.out.println("Goofbye");
    }
}