package co.zelez.plugin.telegram;

import co.zelez.core.command.reader.entity.Param;
import co.zelez.core.command.reader.usecase.ReaderService;
import co.zelez.core.command.reader.usecase.ShopReader;
import co.zelez.core.shopping.repository.ShopRepository;
import co.zelez.core.shopping.usecase.ShoppingService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import lombok.Generated;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.HashMap;

public class TelegramManager implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient telegramClient;
    private final HashMap<Long, ShopRepository> repository = new HashMap<>();

    public TelegramManager(String botToken, TelegramClient client) {
        telegramClient = client;

        try {
            Gson gson = new Gson();
            Type type = new TypeToken<HashMap<Long, ShopRepository>>() { }.getType();
            JsonReader jsonReader = new JsonReader(new FileReader("storage/data.json"));
            HashMap<Long, ShopRepository> jsonMap = gson.fromJson(jsonReader, type);

            if (jsonMap != null) {
                repository.putAll(jsonMap);
            }

            try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
                botsApplication.registerBot(botToken, this);
                System.out.println("Successfully started!");
                Thread.currentThread().join();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public TelegramManager(TelegramClient client) {
        telegramClient = client;
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
            try {
                Gson gson = new Gson();

                String json = gson.toJson(repository);
                FileWriter file = new FileWriter("storage/data.json");
                file.write(json);
                file.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
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
}
