package co.zelez.core.shopping.repository;

import co.zelez.core.common.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShopRepositoryTest {

    @Test
    void Given_name_and_data_When_name_not_exists_Then_create_in_DB()
            throws NoSuchFieldException, IllegalAccessException {
        //Arrange
        IShopRepository shopRepository = new ShopRepository();

        String itemName = "soda";
        Tuple<Float, Integer> data = new Tuple<>(5.0f, 1);

        Field itemsField = ShopRepository.class.getDeclaredField("listDB");
        itemsField.setAccessible(true);

        HashMap<String, Tuple<Float, Integer>> expectedDB = new HashMap<>(Collections.singletonMap(itemName, data));

        //Act
        shopRepository.setItem(itemName, data);

        //Assert
        assertEquals(expectedDB, itemsField.get(shopRepository));
    }

    @Test
    void Given_name_and_data_When_name_exists_Then_update_in_DB()
            throws NoSuchFieldException, IllegalAccessException {
        //Arrange
        IShopRepository shopRepository = new ShopRepository();

        String itemName = "soda";
        Tuple<Float, Integer> data = new Tuple<>(5.0f, 1);
        Tuple<Float, Integer> dataUpdate = new Tuple<>(6.0f, 2);

        Field itemsField = ShopRepository.class.getDeclaredField("listDB");
        itemsField.setAccessible(true);
        HashMap<String, Tuple<Float, Integer>> testItems = new HashMap<>(Collections.singletonMap(itemName, data));
        itemsField.set(shopRepository, testItems);

        HashMap<String, Tuple<Float, Integer>> expectedDB = new HashMap<>(Collections.singletonMap(itemName, dataUpdate));

        //Act
        shopRepository.setItem(itemName, dataUpdate);

        //Assert
        assertEquals(expectedDB, itemsField.get(shopRepository));
    }

    @Test
    void getItem() throws NoSuchFieldException, IllegalAccessException {
        //Arrange
        IShopRepository shopRepository = new ShopRepository();

        String itemName = "soda";
        Tuple<Float, Integer> data = new Tuple<>(5.0f, 1);

        Field itemsField = ShopRepository.class.getDeclaredField("listDB");
        itemsField.setAccessible(true);
        HashMap<String, Tuple<Float, Integer>> testItems = new HashMap<>(Collections.singletonMap(itemName, data));
        itemsField.set(shopRepository, testItems);

        //Act
        String expectedReturn = String.format("%s %s: %s", data.y(), itemName, data.x());
        String actualReturn = shopRepository.getItem(itemName);

        //Assert
        assertEquals(expectedReturn, actualReturn);
    }

    @Test
    void Given_name_item_When_exists_Then_remove_in_DB()
            throws IllegalAccessException, NoSuchFieldException {
        //Arrange
        ShopRepository shopRepository = new ShopRepository();
        String itemName = "soda";

        Field itemsField = ShopRepository.class.getDeclaredField("nameDB");
        itemsField.setAccessible(true);

        HashMap<Integer, String> testItems = new HashMap<>();
        testItems.put(1, "bread");
        testItems.put(2, "cheese");
        testItems.put(3, itemName);
        testItems.put(4, "tomato");

        itemsField.set(shopRepository, testItems);

        HashMap<Integer, String> expectedDB = new HashMap<>();
        expectedDB.put(1, "bread");
        expectedDB.put(2, "cheese");
        expectedDB.put(3, "tomato");

        //Act
        shopRepository.removeItem(itemName);

        //Assert
        assertEquals(expectedDB, itemsField.get(shopRepository));
    }

    @Test
    void Given_name_and_quantityBigger_item_When_exists_Then_remove_in_DB()
            throws IllegalAccessException, NoSuchFieldException {
        //Arrange
        ShopRepository shopRepository = spy(ShopRepository.class);
        String itemName = "soda";
        Tuple<Float, Integer> data = new Tuple<>(5.0f, 1);
        int quantityInput = 2;

        Field itemsField = ShopRepository.class.getDeclaredField("listDB");
        itemsField.setAccessible(true);
        HashMap<String, Tuple<Float, Integer>> testItems = new HashMap<>(Collections.singletonMap(itemName, data));
        itemsField.set(shopRepository, testItems);

        //Act
        shopRepository.removeItem(itemName, quantityInput);

        //Assert
        verify(shopRepository).removeItem(itemName);
    }

    @Test
    void Given_name_and_quantitySmaller_item_When_exists_Then_remove_in_DB()
            throws IllegalAccessException, NoSuchFieldException {
        //Arrange
        ShopRepository shopRepository = spy(ShopRepository.class);
        String itemName = "soda";
        Tuple<Float, Integer> data = new Tuple<>(5.0f, 2);
        int quantityInput = 1;

        Field itemsField = ShopRepository.class.getDeclaredField("listDB");
        itemsField.setAccessible(true);
        HashMap<String, Tuple<Float, Integer>> testItems = new HashMap<>(Collections.singletonMap(itemName, data));
        itemsField.set(shopRepository, testItems);

        //Act
        shopRepository.removeItem(itemName, quantityInput);

        //Assert
        verify(shopRepository).setItem(itemName, new Tuple<>(data.x(), data.y() - quantityInput));
    }

    @Test
    void Given_id_item_When_exists_Then_return_item_name()
            throws NoSuchFieldException, IllegalAccessException {
        //Arrange
        IShopRepository shopRepository = new ShopRepository();

        int itemId = 1;
        String expectedItem = "soda";

        Field itemsField = ShopRepository.class.getDeclaredField("nameDB");
        itemsField.setAccessible(true);

        HashMap<Integer, String> testItems = new HashMap<>(Collections.singletonMap(itemId, expectedItem));
        itemsField.set(shopRepository, testItems);

        //Act
        String nameReturned = shopRepository.itemName(itemId);

        //Assert
        assertEquals(expectedItem, nameReturned);
    }

    @Test
    void Given_id_item_When_not_exists_Then_return_null() {
        //Arrange
        IShopRepository shopRepository = new ShopRepository();

        int itemId = 1;

        //Act
        String nameReturned = shopRepository.itemName(itemId);

        //Assert
        assertNull(nameReturned);
    }

    @Test
    void Given_name_item_When_contains_Then_return_true()
            throws NoSuchFieldException, IllegalAccessException {
        //Arrange
        IShopRepository shopRepository = new ShopRepository();

        String itemName = "soda";
        Tuple<Float, Integer> data = new Tuple<>(5.0f, 1);

        Field itemsField = ShopRepository.class.getDeclaredField("listDB");
        itemsField.setAccessible(true);

        HashMap<String, Tuple<Float, Integer>> testItems = new HashMap<>(Collections.singletonMap(itemName, data));
        itemsField.set(shopRepository, testItems);

        //Act
        boolean boolReturned = shopRepository.listContains(itemName);

        //Assert
        assertTrue(boolReturned);
    }
    @Test
    void Given_name_item_When_not_contains_Then_return_false() {
        //Arrange
        IShopRepository shopRepository = new ShopRepository();

        String itemName = "soda";

        //Act
        boolean boolReturned = shopRepository.listContains(itemName);

        //Assert
        assertFalse(boolReturned);
    }

    @Test
    void When_DB_not_empty_Then_return_shop_list()
            throws NoSuchFieldException, IllegalAccessException {
        //Arrange
        IShopRepository shopRepository = new ShopRepository();

        String itemName = "soda";
        Tuple<Float, Integer> data = new Tuple<>(5.0f, 1);

        Field itemsField = ShopRepository.class.getDeclaredField("listDB");
        itemsField.setAccessible(true);
        HashMap<String, Tuple<Float, Integer>> testItems = new HashMap<>(Collections.singletonMap(itemName, data));
        itemsField.set(shopRepository, testItems);

        //Act
        HashMap<String, Tuple<Float, Integer>> listReturned = shopRepository.getShopList();

        //Assert
        assertEquals(itemsField.get(shopRepository), listReturned);
    }

    @Test
    void When_DB_empty_Then_return_null() {
        //Arrange
        IShopRepository shopRepository = new ShopRepository();

        //Act
        HashMap<String, Tuple<Float, Integer>> listReturned = shopRepository.getShopList();

        //Assert
        assertNull(listReturned);
    }

    @Test
    void When_DB_empty_Then_return_list_is_empty() {
        //Arrange
        IShopRepository shopRepository = new ShopRepository();

        String expectedString = "List is empty";

        //Act
        String stringReturned = shopRepository.outputList();

        //Assert
        assertEquals(expectedString, stringReturned);
    }

    @Test
    void When_item_in_DB_Then_return_list() throws NoSuchFieldException, IllegalAccessException {
        //Arrange
        IShopRepository shopRepository = new ShopRepository();

        String itemName = "soda";
        Tuple<Float, Integer> data = new Tuple<>(5.0f, 2);

        Field itemsField = ShopRepository.class.getDeclaredField("listDB");
        itemsField.setAccessible(true);
        HashMap<String, Tuple<Float, Integer>> testItems = new HashMap<>(Collections.singletonMap(itemName, data));
        itemsField.set(shopRepository, testItems);

        Field namesField = ShopRepository.class.getDeclaredField("nameDB");
        namesField.setAccessible(true);
        HashMap<Integer, String> testNames = new HashMap<>(Collections.singletonMap(1, itemName));
        namesField.set(shopRepository, testNames);

        String expectedString ="Shop List:\n" +
                "[" + 1 + "] - " + String.format("%s %s: %s", data.y(), itemName, data.x()) + "\n\n" +
                "Total Cost: " + data.x() * data.y() + "\n";

        //Act
        String stringReturned = shopRepository.outputList();

        //Assert
        assertEquals(expectedString, stringReturned);
    }

    @Test
    void When_remove_all_items_Then_all_DB_empty() throws NoSuchFieldException, IllegalAccessException {
        //Arrange
        IShopRepository shopRepository = spy(new ShopRepository());

        String itemName = "soda";
        Tuple<Float, Integer> data = new Tuple<>(5.0f, 2);

        Field itemsField = ShopRepository.class.getDeclaredField("listDB");
        itemsField.setAccessible(true);
        HashMap<String, Tuple<Float, Integer>> testItems = mock();
        itemsField.set(shopRepository, testItems);

        Field namesField = ShopRepository.class.getDeclaredField("nameDB");
        namesField.setAccessible(true);
        HashMap<Integer, String> testNames = mock();
        namesField.set(shopRepository, testNames);

        //Act
        shopRepository.removeAllItems();

        //Assert
        verify(testItems, atLeastOnce()).clear();
        verify(testNames, atLeastOnce()).clear();
    }
}