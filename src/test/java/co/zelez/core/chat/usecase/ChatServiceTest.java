package co.zelez.core.chat.usecase;

import co.zelez.plugin.telegram.TelegramManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChatServiceTest {

    @ParameterizedTest
    @ValueSource(ints = {-1, -10, 100, 0})
    void Given_int_When_out_case_Then_Return_consoleService(int id) {
        //Arrange
        ChatService chatService = new ChatService();
        Class<ConsoleManager> expected = ConsoleManager.class;

        //Act
        IManager returned = chatService.service(id);

        //Assert
        assertEquals(expected, returned.getClass());
    }
/*
    @Test
    void Given_int_When_telegram_case_Then_Return_telegramService() {
        //Arrange
        ChatService chatService = new ChatService();
        Class<TelegramManager> expected = TelegramManager.class;
        int id = 1;

        //Act
        IManager returned = chatService.service(id);

        //Assert
        assertEquals(expected, returned.getClass());
    }
    */
}