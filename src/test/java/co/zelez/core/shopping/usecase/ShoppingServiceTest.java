package co.zelez.core.shopping.usecase;

import co.zelez.core.common.Tuple;
import co.zelez.core.shopping.repository.ShopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShoppingServiceTest {
    private ShopRepository shopRepo;
    private ShoppingService service;

    @BeforeEach
    void setUp() {
        shopRepo = spy(ShopRepository.class);
        service = new ShoppingService(shopRepo);
    }

    @Test
    void Given_an_name_and_data_Then_return_setItem() {
        //Arrange
        String itemName = "soda";
        Tuple<Float, Integer> data = new Tuple<>(5.0f, 1);

        doNothing().when(shopRepo).setItem(itemName, data);

        //Act
        service.addItem(itemName, data);

        //Assert
        verify(shopRepo, atLeastOnce()).setItem(itemName, data);
    }

    @Test
    void Given_an_name_and_quantity_Then_return_setItem() {
        //Arrange
        String itemName = "soda";
        int quantity = 1;
        Tuple<Float, Integer> data = new Tuple<>(5.0f, 1);
        Tuple<Float, Integer> expectedData = new Tuple<>(5.0f, 2);
        HashMap<String, Tuple<Float, Integer>> listDB = new HashMap<>(Collections.singletonMap(itemName, data));

        doReturn(listDB).when(shopRepo).getShopList();
        doNothing().when(shopRepo).setItem(itemName, data);

        //Act
        service.addItem(itemName, quantity);

        //Assert
        verify(shopRepo, atLeastOnce()).setItem(itemName, expectedData);
    }

    @Test
    void Given_an_name_and_quantity_Then_return_call_removeItem() {
        //Arrange
        String itemName = "soda";
        int itemQuantity = 1;

        doNothing().when(shopRepo).removeItem(itemName, itemQuantity);

        //Act
        service.removeItem(itemName, itemQuantity);

        //Assert
        verify(shopRepo, atLeastOnce()).removeItem(itemName, itemQuantity);
    }

    @Test
    void Given_an_name_Then_return_call_removeItem() {
        //Arrange
        String itemName = "soda";

        doNothing().when(shopRepo).removeItem(itemName);

        //Act
        service.removeItem(itemName);

        //Assert
        verify(shopRepo, atLeastOnce()).removeItem(itemName);
    }

    @Test
    void Then_return_call_itemName() {
        //Arrange
        String expectedSting = "correct";

        doReturn(expectedSting).when(shopRepo).itemName(1);

        //Act
        String stringReturned = service.getItemName(1);

        //Assert
        verify(shopRepo, atLeastOnce()).itemName(1);
        assertEquals(expectedSting, stringReturned);
    }

    @Test
    void Then_return_call_contains() {
        //Arrange
        doReturn(true).when(shopRepo).listContains(null);

        //Act
        boolean boolReturned = service.listContains(null);

        //Assert
        verify(shopRepo, atLeastOnce()).listContains(null);
        assertTrue(boolReturned);
    }

    @Test
    void Then_return_call_removeAllItems() {
        //Arrange
        doNothing().when(shopRepo).removeAllItems();

        //Act
        service.clearList();

        //Assert
        verify(shopRepo, atLeastOnce()).removeAllItems();
    }

    @Test
    void Then_return_call_outputList() {
        //Arrange
        String expectedSting = "correct";

        doReturn(expectedSting).when(shopRepo).outputList();

        //Act
        String stringReturned = service.getList();

        //Assert
        verify(shopRepo, atLeastOnce()).outputList();
        assertEquals(expectedSting, stringReturned);
    }
}