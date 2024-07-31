package co.zelez;

import co.zelez.core.command.reader.entity.Param;
import co.zelez.core.command.reader.usecase.ReaderService;

import java.io.Console;
import java.util.Arrays;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ReaderService read = new ReaderService();
        String input = System.console().readLine("Input: ");

        Param param = read.buildParam(input);
        System.out.println(
                param.getType() + "\n"
                +param.getCommand() + "\n"
                + Arrays.toString(param.getArgs()) + "\n"
        );
    }
}