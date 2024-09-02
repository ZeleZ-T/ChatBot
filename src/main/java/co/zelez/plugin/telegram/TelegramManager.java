package co.zelez.plugin.telegram;

import co.zelez.core.command.reader.entity.Param;
import co.zelez.core.command.reader.usecase.ReaderService;
import co.zelez.core.command.reader.usecase.ShopReader;
import co.zelez.core.common.FileManager;
import co.zelez.core.shopping.repository.ShopRepository;
import co.zelez.core.shopping.usecase.ShoppingService;

import lombok.Generated;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.AfterBotRegistration;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.HashMap;

@Component
public class TelegramManager implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {
    private final HashMap<Long, ShopRepository> repository = new HashMap<>();
    private final TelegramClient telegramClient;
    private final FileManager fileManager;
    private final String botToken;

    public TelegramManager() {
        this(System.getenv("TELEGRAM_TOKEN"),
                new OkHttpTelegramClient(System.getenv("TELEGRAM_TOKEN")),
                new FileManager("storage/data.json"));
    }

    public TelegramManager(String botToken, TelegramClient telegramClient, FileManager fileManager) {
        this.botToken = botToken;
        this.telegramClient = telegramClient;
        this.fileManager = fileManager;
        repository.putAll(fileManager.getShopData());
        System.out.println("Successfully started!");
    }


    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            ReaderService reader;

            if (!repository.containsKey(chat_id)) {
                repository.put(chat_id, new ShopRepository());
                SendMessage message = SendMessage
                        .builder()
                        .chatId(chat_id)
                        .text(getWelcomeMessage(update))
                        .build();
                try {
                    telegramClient.execute(message);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            reader = readerService(repository.get(chat_id));
            Param param = reader.buildParam(message_text);
            String response = reader.readParam(param);

            SendMessage message = SendMessage
                    .builder()
                    .chatId(chat_id)
                    .text(response)
                    .build();
            try {
                telegramClient.execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
            fileManager.setShopData(repository);
        }
    }

    @Generated
    private String getWelcomeMessage(Update update) {
        String userName = update.getMessage().getFrom().getFirstName();

        return String.format("""
                Hi %s \uD83D\uDC4B
                I'm Shopping bot with the mission of help your daily purchases \uD83D\uDED2
                """, userName);
    }

    @Generated
    private ReaderService readerService(ShopRepository repository) {
        ShoppingService service = new ShoppingService(repository);
        ShopReader reader = new ShopReader(service);
        return new ReaderService(reader);
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @AfterBotRegistration
    public void afterRegistration(BotSession botSession) {
        System.out.println("Registered bot running state is: " + botSession.isRunning());
    }
}
