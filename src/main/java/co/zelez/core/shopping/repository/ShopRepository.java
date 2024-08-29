package co.zelez.core.shopping.repository;

import co.zelez.core.common.Tuple;
import lombok.Generated;
import lombok.Getter;

import java.util.*;

public class ShopRepository implements IShopRepository {
    private final HashMap<String, Tuple<Float, Integer>> listDB = new HashMap<>();
    private final HashMap<Integer, String> nameDB = new HashMap<>();

    @Override
    public void setItem(String name, Tuple<Float, Integer> data) {
        if (!listContains(name)) {
            listDB.put(name, data);
            nameDB.put(listDB.size(), name);
        } else listDB.put(name, new Tuple<>(data.x(), data.y()));
    }

    @Override
    public String getItem(String name) {
        return String.format("%s %s: %s", listDB.get(name).y(), name, listDB.get(name).x());
    }

    @Override
    public void removeItem(String name) {
        boolean remove = false;
        for (Integer key : nameDB.keySet()) {
            if (!remove) remove = nameDB.get(key).equals(name);
            if (remove && key != nameDB.size()) {
                nameDB.put(key, nameDB.get(key+1));
            } else if (key == nameDB.size()) nameDB.remove(key);
        }
        listDB.remove(name);
    }

    @Override
    public void removeAllItems() {
        listDB.clear();
        nameDB.clear();
    }

    @Override
    public void removeItem(String name, int quantity) {
        if (listDB.get(name).y() > quantity) {
            setItem(name, new Tuple<>(listDB.get(name).x(), listDB.get(name).y() - quantity));
        } else removeItem(name);
    }

    @Override
    public String itemName(int id) {
        return nameDB.getOrDefault(id, null);
    }

    @Override
    public boolean listContains(String name) {
        return listDB.containsKey(name);
    }

    @Override
    public HashMap<String, Tuple<Float, Integer>> getShopList() {
        return listDB.isEmpty() ? null : listDB;
    }

    @Override
    public String outputList() {
        if (listDB.isEmpty()) return "List is empty";

        float totalCost = 0f;
        StringBuilder listOutput = new StringBuilder();
        listOutput.append("Shop List:\n");

        for (int i = 1; i <= listDB.size(); i++) {
            String key = itemName(i);

            Tuple<Float, Integer> value = listDB.get(key);
            totalCost += value.x() * value.y();

            String out = "[" + i + "] - " + getItem(key);
            listOutput.append(out).append("\n");
        }
        listOutput.append("\n").append("Total Cost: ").append(totalCost).append("\n");
        return listOutput.toString();
    }
}