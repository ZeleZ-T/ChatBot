package co.zelez.core.shopping.usecase;

import co.zelez.core.common.Tuple;
import co.zelez.core.shopping.repository.IShopRepository;
import lombok.AllArgsConstructor;

import java.util.HashMap;

@AllArgsConstructor
public class ShoppingService implements IShoppingService{
    private final IShopRepository shopRepository;

    @Override
    public void addItem(String name, Tuple<Float, Integer> data) {
        shopRepository.setItem(name, data);
    }

    @Override
    public void addItem(String name, int quantity) {
        quantity += shopRepository.getShopList().get(name).y();
        shopRepository.setItem(name,
                new Tuple<>(shopRepository.getShopList().get(name).x(),
                        quantity));
    }

    @Override
    public void addItem(String name) {
        addItem(name, 1);
    }

    @Override
    public void removeItem(String name, int quantity) {
        shopRepository.removeItem(name, quantity);
    }

    @Override
    public void removeItem(String name) {
        shopRepository.removeItem(name);
    }

    @Override
    public String getItemName(int id) {
        return shopRepository.itemName(id);
    }

    @Override
    public boolean listContains(String name) {
        return shopRepository.listContains(name);
    }

    @Override
    public String getList() {
        return shopRepository.outputList();
    }
}
