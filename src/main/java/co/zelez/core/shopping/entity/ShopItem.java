package co.zelez.core.shopping.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShopItem {
    private String itemName;
    private int itemPrice;
    private @Setter int itemQuantity;
}
