package co.zelez.core.command.reader.usecase;

import co.zelez.core.command.reader.entity.Param;
import co.zelez.core.command.reader.entity.ReadType;

import java.util.Arrays;

public class ReaderService {
    public Param buildParam(String input) {
        String[] parts = input.split(" ");

        String rawType = parts[0].toUpperCase();
        ReadType readType;

        boolean typeExist = Arrays.toString(ReadType.values()).contains(rawType);
        if (typeExist) readType = ReadType.valueOf(rawType);
        else readType = ReadType.HELP;

        return Param.builder().
                type(readType).
                command(parts[1]).
                args(Arrays.copyOfRange(parts, 2, parts.length)).
                build();
    }
}
