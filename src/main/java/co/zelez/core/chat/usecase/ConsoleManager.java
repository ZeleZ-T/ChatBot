package co.zelez.core.chat.usecase;

import co.zelez.core.command.reader.entity.Param;
import co.zelez.core.command.reader.usecase.ReaderService;

public class ConsoleManager implements IManager {
    @Override
    public void chat() {
        ReaderService read = new ReaderService();
        String input = "";
        Param param;

        do {
            System.out.print("-");

            input = System.console().readLine();
            param = read.buildParam(input);

            System.out.println(read.readParam(param));
        } while (!input.isEmpty());
    }
}
