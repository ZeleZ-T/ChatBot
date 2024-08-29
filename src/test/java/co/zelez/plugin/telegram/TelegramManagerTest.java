package co.zelez.plugin.telegram;

import co.zelez.core.command.reader.usecase.ReaderService;
import co.zelez.core.common.Tuple;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.lang.reflect.Field;
import java.util.HashMap;

import static co.zelez.core.command.reader.usecase.HelpReader.help;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TelegramManagerTest {

    TelegramClient client;
    TelegramManager manager;

    @BeforeEach
    void setUp() {
        client = mock();
        manager = new TelegramManager(client);
    }

    @Test
    void Given_update_When_first_time_chat_Then_welcomeMessage() throws TelegramApiException {
        //Arrange
        Update update = mock();

        long chatId = 123;
        String username = "ZeleZ";
        Message message = mock();
        User user = mock();

        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(message);
        when(message.getChatId()).thenReturn(chatId);
        when(message.hasText()).thenReturn(true);
        when(message.getFrom()).thenReturn(user);
        when(message.getText()).thenReturn("");
        when(user.getFirstName()).thenReturn(username);

        when(client.execute(any(SendMessage.class))).thenReturn(null);

        String welcomeText = String.format("""
                Hi %s \uD83D\uDC4B
                I'm Shopping bot with the mission of help your daily purchases \uD83D\uDED2
                """, username);
        SendMessage welcome = SendMessage
                .builder()
                .chatId(chatId)
                .text(welcomeText)
                .build();

        SendMessage helpMessage = SendMessage
                .builder()
                .chatId(chatId)
                .text(help())
                .build();

        //Act
        manager.consume(update);

        //Assert
        verify(client, times(2)).execute(any(SendMessage.class));
    }
}