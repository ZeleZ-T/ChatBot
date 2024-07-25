package co.zelez.core.shopping.repository;

import co.zelez.core.shopping.entity.ShopItem;

import java.util.List;

public interface IShopRepository {
    void addItem(ShopItem item);
    void updateItemByID(int ID, ShopItem item);
    void removeItemByID(int ID);
    ShopItem getItemByID(int ID);
    int getIDByName(String name);

    List<ShopItem> getShopList();
}