package co.zelez.plugin.telegram;

import co.zelez.core.common.FileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.HashMap;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TelegramManagerTest {

    @Mock
    FileManager fileManager;
    @Mock
    TelegramClient telegramClient;

    @Mock
    private Update update;

    @Mock
    private Message message;

    @Mock
    private User user;

    TelegramManager telegramManager;

    @BeforeEach
    void setUp(){
        String botToken = "test_bot_token";
        when(fileManager.getShopData()).thenReturn(new HashMap<>());
        telegramManager = new TelegramManager(botToken, telegramClient, fileManager);
    }

    @Test
    void Given_update_When_first_time_chat_Then_welcomeMessage() throws TelegramApiException {
        //Arrange
        long chatId = 12323;
        String messageText = "example";
        String userName = "TestUser";

        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(message);
        when(message.hasText()).thenReturn(true);
        when(message.getText()).thenReturn(messageText);
        when(message.getChatId()).thenReturn(chatId);
        when(message.getFrom()).thenReturn(user);
        when(user.getFirstName()).thenReturn(userName);

        //Act
        telegramManager.consume(update);

        //Assert
        verify(telegramClient, times(2)).execute(any(SendMessage.class));
        verify(fileManager, atLeastOnce()).setShopData(any());
    }
}
