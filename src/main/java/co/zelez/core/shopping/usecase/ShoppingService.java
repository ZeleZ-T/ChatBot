package co.zelez.core.shopping.usecase;

import co.zelez.core.shopping.dto.ItemInDTO;
import co.zelez.core.shopping.dto.SearchInDTO;
import co.zelez.core.shopping.entity.ShopItem;
import co.zelez.core.shopping.repository.IShopRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ShoppingService {
    private final IShopRepository shopRepository;

    public void removeItem(SearchInDTO searchDTO){
        int ID = (searchDTO.ID() != -1) ? searchDTO.ID() : shopRepository.getIDByName(searchDTO.name());
        int quantity = searchDTO.quantity();

        if(quantity == -1 ||
                quantity >= (shopRepository.getItemByID(ID).getItemQuantity())) {

            shopRepository.removeItemByID(ID);
            return;
        }
        ShopItem item = shopRepository.getItemByID(ID);
        item.setItemQuantity(item.getItemQuantity() - quantity);
        shopRepository.updateItemByID(ID, item);
    }

    public void anotherItem(SearchInDTO searchDTO){
        int ID = (searchDTO.ID() != -1) ? searchDTO.ID() : shopRepository.getIDByName(searchDTO.name());
        int quantity = (searchDTO.quantity() < 0) ? searchDTO.quantity() : 1;

        ShopItem item = shopRepository.getItemByID(ID);
        item.setItemQuantity(item.getItemQuantity() + quantity);
        shopRepository.updateItemByID(ID, item);
    }

    public void addItem(ItemInDTO itemDTO){
        shopRepository.addItem(new ShopItem(itemDTO.name(), itemDTO.price(), itemDTO.quantity()));
    }

    public int getTotalCost() {
        int totalCost = 0;
        List<ShopItem> list = this.shopRepository.getShopList();
        for (ShopItem item : list) {
            totalCost += item.getItemPrice() * item.getItemQuantity();
        }
        return totalCost;
    }
}
