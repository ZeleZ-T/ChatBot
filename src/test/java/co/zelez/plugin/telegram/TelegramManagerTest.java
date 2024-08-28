package co.zelez.plugin.telegram;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class TelegramManagerTest {

    @Test
    void Given_update_When_first_time_chat_Then_welcomeMessage() {
        //Arrange
        String bBotToken = "BotToken";
        TelegramClient client = mock();
        TelegramManager manager = new TelegramManager(bBotToken, client);
    }
}