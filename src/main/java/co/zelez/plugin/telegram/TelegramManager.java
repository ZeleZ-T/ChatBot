package co.zelez.plugin.telegram;

import co.zelez.core.command.reader.entity.Param;
import co.zelez.core.command.reader.usecase.HelpReader;
import co.zelez.core.command.reader.usecase.ReaderService;
import lombok.Generated;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.HashMap;

public class TelegramManager implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient telegramClient;
    private final HashMap<Long, ReaderService> readerServices = new HashMap<>();

    public TelegramManager(String botToken, TelegramClient client) {
        telegramClient = client;
        try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
            botsApplication.registerBot(botToken, this);
            System.out.println("Successfully started!");
            Thread.currentThread().join();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            ReaderService readerService;

            if (!readerServices.containsKey(chat_id)) {
                readerServices.put(chat_id, new ReaderService());
                SendMessage message = SendMessage
                        .builder()
                        .chatId(chat_id)
                        .text(getWelcomeMessage(update))
                        .build();
                SendMessage help = SendMessage
                        .builder()
                        .chatId(chat_id)
                        .text(HelpReader.help())
                        .build();
                try {
                    telegramClient.execute(message);
                    telegramClient.execute(help);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                readerService = readerServices.get(chat_id);
                Param param = readerService.buildParam(message_text);
                String response = readerService.readParam(param);

                SendMessage message = SendMessage
                        .builder()
                        .chatId(chat_id)
                        .text(response)
                        .build();
                try {
                    telegramClient.execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
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
}
