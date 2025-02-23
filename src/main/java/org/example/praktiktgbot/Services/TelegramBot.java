package org.example.praktiktgbot.Services;
import org.example.praktiktgbot.Entity.TelegramProperties;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class TelegramBot extends TelegramLongPollingBot {

    private final TelegramProperties properties;

    private final GptService gptService;



    public TelegramBot(TelegramProperties telegramProperties,
                       TelegramBotsApi telegramBotsApi, GptService gptService) throws TelegramApiException {
        this.properties = telegramProperties;
        this.gptService = gptService;
        telegramBotsApi.registerBot(this);
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            execute(new SendMessage(update.getMessage().getChatId().toString(), gptService.answer(update.getMessage().getText())));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return properties.getUsername();
    }

    @Override
    public String getBotToken() {
        return properties.getToken();
    }
}