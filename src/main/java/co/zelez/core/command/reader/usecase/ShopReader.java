package co.zelez.core.command.reader.usecase;

import co.zelez.core.command.reader.entity.Param;
import co.zelez.core.command.reader.entity.ReadType;
import co.zelez.core.common.Tuple;
import co.zelez.core.common.Util;
import co.zelez.core.shopping.usecase.IShoppingService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShopReader implements IReader {
    private final IShoppingService service;

    @Override
    public String read(Param param) {
        if (param.getType() != ReadType.SHOP) return null;

        return switch (param.getCommand().toLowerCase()) {
            case "remove", "r" -> remove(param);
            case "add", "a" -> add(param);
            case "total", "list", "" -> service.getList();
            default -> null;
        };
    }

    public String add(Param param) {
        String name = paramGetName(param);
        if (!service.listContains(name)) {
            int quantity = paramGetQuantity(param, 3);
            float price = 0f;

            if (param.getArgs().length >= 2 && Util.isNumeric(param.getArgs()[1])) {
                price = Float.parseFloat(param.getArgs()[1]);
            } else return null;

            service.addItem(name, new Tuple<>(price, quantity > 0 ? quantity : 1));
            return name + " Added" + "\n\n" + service.getList();
        } else {
            int quantity = paramGetQuantity(param, 2);
            if (quantity > 0) {
                service.addItem(name, quantity);
                return quantity + " " + name + " Added " + "\n\n" + service.getList();
            }
            else {
                service.addItem(name);
                return name + " Added" + "\n\n" + service.getList();
            }
        }
    }

    public String remove(Param param) {
        String name = paramGetName(param);
        if (service.listContains(name)) {
            int quantity = paramGetQuantity(param, 2);
            if (quantity > 0) {
                service.removeItem(name, quantity);
                return quantity + " " + name + " Removed" + "\n\n" + service.getList();
            } else {
                service.removeItem(name);
                return "All " + name + " Removed" + "\n\n" + service.getList();
            }
        } else return null;
    }

    private String paramGetName(Param param) {
        return !Util.isNumeric(param.getArgs()[0]) ?
                param.getArgs()[0] :
                service.getItemName(Integer.parseInt(param.getArgs()[0]));
    }
    private int paramGetQuantity(Param param, int position) {
        position--;
        return param.getArgs().length > position && Util.isNumeric(param.getArgs()[position]) ?
                Integer.parseInt(param.getArgs()[position]) : -1;
    }
}
