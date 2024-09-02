package co.zelez;

import co.zelez.plugin.telegram.TelegramManager;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.telegram.telegrambots.longpolling.starter.TelegramBotInitializer;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChatBotApplicationTest {

    @MockBean
    private TelegramManager telegramManager;

    @MockBean
    private TelegramBotInitializer telegramBotInitializer;

    @InjectMocks
    private ChatBotApplication chatBotApplication;

    @Test
    void contextLoad() {
    }
}