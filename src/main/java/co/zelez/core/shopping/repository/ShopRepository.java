package co.zelez.core.shopping.repository;

import co.zelez.core.shopping.entity.ShopItem;

import java.util.ArrayList;
import java.util.List;

public class ShopRepository implements IShopRepository {
    private List<ShopItem> listDB = new ArrayList<ShopItem>();

    @Override
    public void addItem(ShopItem item) {
        this.listDB.add(item);
    }

    @Override
    public void updateItemByID(int ID, ShopItem item) {
        this.listDB.set(ID, item);
    }

    @Override
    public void removeItemByID(int ID) {
        this.listDB.remove(ID);
    }

    @Override
    public ShopItem getItemByID(int ID) {
        return this.listDB.get(ID);
    }

    @Override
    public int getIDByName(String name) {
        return 0;
    }

    @Override
    public List<ShopItem> getShopList() {
        return List.of();
    }
}
