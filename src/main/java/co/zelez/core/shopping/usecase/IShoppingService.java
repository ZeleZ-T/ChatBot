package co.zelez.core.shopping.usecase;

import co.zelez.core.common.Tuple;

public interface IShoppingService {
    void addItem(String name, Tuple<Float, Integer> data);
    void addItem(String name, int quantity);
    void addItem(String name);

    void removeItem(String name, int quantity);
    void removeItem(String name);

    void clearList();

    String getItemName(int id);
    boolean listContains(String name);

    String getList();
}
