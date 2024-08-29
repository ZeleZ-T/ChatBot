package co.zelez.core.chat.usecase;

import co.zelez.plugin.telegram.TelegramManager;
import lombok.Generated;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;

public class ChatService {
    private ChatService() {}

    @Generated
    public static void chat(int i) {
        String botToken = System.getenv("TELEGRAM_TOKEN");
        switch (i) {
            case 1 -> new TelegramManager(botToken, new OkHttpTelegramClient(botToken));
            default -> new ConsoleManager();
        };
    }
}
