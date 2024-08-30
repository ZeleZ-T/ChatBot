package co.zelez.core.common;

import co.zelez.core.shopping.repository.ShopRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileManagerTest {

    File file = mock();

    FileManager fileManager;

    @Test
    void Given_file_When_not_exist_Then_return_new_hashmap() throws IOException {
        //Arrange
        HashMap<Long, ShopRepository> hashmapExpected = new HashMap<>();
        when(file.exists()).thenReturn(false);

        fileManager = new FileManager(file);

        //Act
        HashMap<Long, ShopRepository> repository = fileManager.getShopData();

        //Assert
        assertEquals(hashmapExpected, repository);
    }
}