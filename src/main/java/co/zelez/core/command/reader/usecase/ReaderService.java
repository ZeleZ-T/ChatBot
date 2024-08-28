package co.zelez.core.command.reader.usecase;

import co.zelez.core.command.reader.entity.Param;
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

        return Param.builder().
                command(parts[0]).
                args(parts.length > 1 ? Arrays.copyOfRange(parts, 1, parts.length) : new String[0]).
                build();
    }
}
