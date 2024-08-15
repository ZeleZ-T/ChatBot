package co.zelez.core.command.reader.usecase;

import co.zelez.core.command.reader.entity.Param;
import co.zelez.core.command.reader.entity.ReadType;
import co.zelez.core.shopping.repository.ShopRepository;
import co.zelez.core.shopping.usecase.ShoppingService;

import java.util.Arrays;

public class ReaderService {
    private final IReader[] readers = {
      new ShopReader(new ShoppingService(new ShopRepository())),
      new HelpReader()
    };

    public String readParam(Param param) {
        for (IReader reader : readers) {
            String answer = reader.read(param);
            if (answer != null) return answer;
        }
        return "ERROR";
    }

    public Param buildParam(String input) {
        String[] parts = input.split(" ");

        String rawType = parts[0].toUpperCase();
        ReadType readType = selectType(rawType);

        return Param.builder().
                type(readType).
                command(parts.length > 1 ? parts[1] : "").
                args(parts.length > 2 ? Arrays.copyOfRange(parts, 2, parts.length) : new String[0]).
                build();
    }

    private ReadType selectType(String input) {
        return switch (input) {
            case "SHOP" -> ReadType.SHOP;
            default -> ReadType.HELP;
        };
    }
}
