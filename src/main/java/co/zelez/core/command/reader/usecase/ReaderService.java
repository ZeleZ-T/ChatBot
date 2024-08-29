package co.zelez.core.command.reader.usecase;

import co.zelez.core.command.reader.entity.Param;
import co.zelez.core.shopping.repository.ShopRepository;
import co.zelez.core.shopping.usecase.ShoppingService;
import lombok.AllArgsConstructor;

import java.util.Arrays;

import static co.zelez.core.command.reader.usecase.HelpReader.help;

@AllArgsConstructor
public class ReaderService {
    private final ShopReader shopReader;

    public ReaderService() {
        ShopRepository shopRepository = new ShopRepository();
        ShoppingService shoppingService = new ShoppingService(shopRepository);
        this.shopReader = new ShopReader(shoppingService);
    }

    public String readParam(Param param) {
            String answer = shopReader.read(param);
            if (answer != null) return answer;
            else return help();
    }

    public Param buildParam(String input) {
        String[] parts = input.split(" ");

        return Param.builder().
                command(parts[0]).
                args(parts.length > 1 ? Arrays.copyOfRange(parts, 1, parts.length) : new String[0]).
                build();
    }
}
