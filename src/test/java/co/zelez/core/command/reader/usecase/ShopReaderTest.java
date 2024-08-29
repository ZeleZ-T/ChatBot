package co.zelez.core.command.reader.usecase;

import co.zelez.core.command.reader.entity.Param;
import co.zelez.core.shopping.usecase.ShoppingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShopReaderTest {

    @ParameterizedTest
    @ValueSource(strings = {"r", "remove"})
    void Given_remove_Then_call_remove(String input) {
        //Arrange
        ShopReader shopReader = new ShopReader(mock(ShoppingService.class));
        ShopReader spyShopReader = spy(shopReader);
        String expectedReturn = "correct";

        Param param = Mockito.mock(Param.class);
        when(param.getCommand()).thenReturn(input);

        doReturn(expectedReturn).when(spyShopReader).remove(param);

        //Act
        String stringReturned = spyShopReader.read(param);

        //Assert
        verify(spyShopReader, atLeastOnce()).remove(param);
        assertEquals(expectedReturn, stringReturned);
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "add"})
    void Given_add_Then_call_add(String input) {
        //Arrange
        ShopReader shopReader = new ShopReader(mock(ShoppingService.class));
        ShopReader spyShopReader = spy(shopReader);
        String expectedReturn = "correct";

        Param param = Mockito.mock(Param.class);
        when(param.getCommand()).thenReturn(input);

        doReturn(expectedReturn).when(spyShopReader).add(param);

        //Act
        String stringReturned = spyShopReader.read(param);

        //Assert
        verify(spyShopReader, atLeastOnce()).add(param);
        assertEquals(expectedReturn, stringReturned);
    }

    @ParameterizedTest
    @ValueSource(strings = {"total", "list"})
    void Given_list_Then_call_service_getList(String input) {
        //Arrange
        ShoppingService shoppingService = spy(new ShoppingService(null));
        ShopReader shopReader = new ShopReader(shoppingService);

        String expectedReturn = "correct";

        Param param = Mockito.mock(Param.class);
        when(param.getCommand()).thenReturn(input);

        doReturn(expectedReturn).when(shoppingService).getList();

        //Act
        String stringReturned = shopReader.read(param);

        //Assert
        verify(shoppingService, atLeastOnce()).getList();
        assertEquals(expectedReturn, stringReturned);
    }

    @Test
    void Given_command_When_not_exist_Then_return_null() {
        //Arrange
        ShopReader shopReader = new ShopReader(null);

        Param param = Mockito.mock(Param.class);
        when(param.getCommand()).thenReturn("wrong");

        //Act
        String stringReturned = shopReader.read(param);

        //Assert
        assertNull(stringReturned);
    }

    @Test
    void Given_remove_name_When_not_exists_Then_return_not_found() {
        //Arrange
        ShoppingService service = Mockito.mock(ShoppingService.class);
        ShopReader shopReader = new ShopReader(service);
        ShopReader spyReader = spy(shopReader);

        String itemName = "soda";
        String[] args = new String[1];
        args[0] = itemName;
        Param param = Param.builder().args(args).build();

        String expectedReturn = itemName + " Not Found";

        doReturn(false).when(service).listContains(itemName);

        //Act
        String returnedString = spyReader.remove(param);

        //Assert
        assertEquals(expectedReturn, returnedString);
    }

    @Test
    void Given_add_name_When_not_exists_Then_return_not_found() {
        //Arrange
        ShoppingService service = Mockito.mock(ShoppingService.class);
        ShopReader spyReader = new ShopReader(service);

        String itemName = "soda";
        String[] args = new String[1];
        args[0] = itemName;
        Param param = Param.builder().args(args).build();

        String expectedReturn = "To add a new item, you must set a price \n" +
                "Add (Item) (Price)";

        doReturn(false).when(service).listContains(itemName);

        //Act
        String returnedString = spyReader.add(param);

        //Assert
        assertEquals(expectedReturn, returnedString);
    }

    @Test
    void Given_add_name_When_exists_Then_return_not_found() {
        //Arrange
        ShoppingService service = Mockito.mock(ShoppingService.class);
        ShopReader shopReader = new ShopReader(service);
        ShopReader spyReader = spy(shopReader);

        String itemName = "soda";
        String[] args = new String[1];
        args[0] = itemName;
        Param param = Param.builder().args(args).build();

        String expectedReturn = itemName + " Added \n\n" + service.getList();


        doReturn(true).when(service).listContains(itemName);

        //Act
        String returnedString = spyReader.add(param);

        //Assert
        assertEquals(expectedReturn, returnedString);
    }


    @Test
    void Given_add_name_quantity_When_exists_Then_return_not_found() {
        //Arrange
        ShoppingService service = mock(ShoppingService.class);
        ShopReader shopReader = new ShopReader(service);
        ShopReader spyReader = spy(shopReader);

        String itemName = "soda";
        String[] args = new String[2];
        args[0] = itemName;
        args[1] = "123";
        Param param = Param.builder().args(args).build();

        when(service.getList()).thenReturn("list");
        String expectedReturn = args[1] + " " + itemName + " Added \n\n" + "list";

        doReturn(true).when(service).listContains(itemName);

        //Act
        String returnedString = spyReader.add(param);

        //Assert
        assertEquals(expectedReturn, returnedString);
    }

    @Test
    void When_list_empty_Then_return_nothing_to_clear() {
        //Arrange
        ShoppingService service = Mockito.mock(ShoppingService.class);
        ShopReader shopReader = new ShopReader(service);
        String expectedReturn = "List is empty, nothing to clear";

        when(service.getList()).thenReturn("List is empty");

        //Act
        String returned = shopReader.clear();

        //Assert
        assertEquals(expectedReturn, returned);
    }

    @Test
    void When_list_not_empty_Then_return_clear() {
        //Arrange
        ShoppingService service = Mockito.mock(ShoppingService.class);
        ShopReader shopReader = new ShopReader(service);
        String expectedReturn = "List cleared";

        when(service.getList()).thenReturn("List isn't empty");

        //Act
        String returned = shopReader.clear();

        //Assert
        assertEquals(expectedReturn, returned);
    }
}