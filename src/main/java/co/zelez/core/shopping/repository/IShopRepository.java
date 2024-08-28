package co.zelez.core.shopping.repository;

import co.zelez.core.common.Tuple;

import java.util.HashMap;

public interface IShopRepository {
    void setItem(String name, Tuple<Float, Integer> data);
    String getItem(String name);

    void removeItem(String name, int quantity);
    void removeItem(String name);

    void removeAllItems();

    String itemName(int id);
    boolean listContains(String name);

    HashMap<String, Tuple<Float, Integer>> getShopList();
    String outputList();
}