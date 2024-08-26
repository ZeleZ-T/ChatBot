package co.zelez.core.command.reader.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ReaderServiceTest {
    ReaderService readerService;

    @BeforeEach
    void setUp() {
        readerService = new ReaderService();
    }

    @ParameterizedTest
    @ValueSource(strings = {"random command", "123", "Abc", "Hello World", "\n Hi \n world", "shop add soda"})
    void Given_any_Then_never_return_errorMessage(String input) {
        //Arrange
        String stringUnexpected = "ERROR";

        //Act
        String stringReturned = readerService.readParam(readerService.buildParam(input));

        //Assert
        assertNotEquals(stringUnexpected, stringReturned);
    }
}