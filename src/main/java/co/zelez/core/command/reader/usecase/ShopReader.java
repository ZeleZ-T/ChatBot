package co.zelez.core.command.reader.usecase;

import co.zelez.core.command.reader.entity.Param;
import co.zelez.core.common.Tuple;
import co.zelez.core.common.Util;
import co.zelez.core.shopping.usecase.IShoppingService;
import lombok.AllArgsConstructor;
import lombok.Generated;

import java.util.Objects;

@AllArgsConstructor
public class ShopReader implements IReader {
    private final IShoppingService service;

    @Override
    public String read(Param param) {
        return switch (param.getCommand().toLowerCase()) {
            case "add", "a" -> add(param);
            case "remove", "r" -> remove(param);
            case "clear", "c" -> clear();
            case "total", "t", "list", "l" -> service.getList();
            default -> null;
        };
    }

    public String add(Param param) {
        String name = paramGetName(param);
        if (!service.listContains(name)) {
            if (Util.isNumeric(name)) return "To add a new item, name can't be a number \n" +
                    "Add (Item) (Price)";

            int quantity = paramGetQuantity(param, 2);
            float price = 0f;

            if (param.getArgs().length >= 2 && Util.isNumeric(param.getArgs()[1])) {
                price = Float.parseFloat(param.getArgs()[1]);
            } else return "To add a new item, you must set a price \n" +
                    "Add (Item) (Price)";

            service.addItem(name, new Tuple<>(price, quantity > 0 ? quantity : 1));
            return name + " Added" + "\n\n" + service.getList();
        } else {
            int quantity = paramGetQuantity(param, 1);
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
        } else {
            name = name != null ? name : param.getArgs()[0];
            return name + " Not Found";
        }
    }

    public String clear() {
        if (!Objects.equals(service.getList(), "List is empty")) {
            service.clearList();
            return "List cleared";
        } else return "List is empty, nothing to clear";
    }

    @Generated
    private String paramGetName(Param param) {

        return  Util.isNumeric(param.getArgs()[0]) &&
                service.getItemName(Integer.parseInt(param.getArgs()[0])) != null ?

                service.getItemName(Integer.parseInt(param.getArgs()[0])) :
                param.getArgs()[0];
    }
    @Generated
    private int paramGetQuantity(Param param, int position) {
        return param.getArgs().length > position && Util.isNumeric(param.getArgs()[position]) ?
                Integer.parseInt(param.getArgs()[position]) : -1;
    }
}
