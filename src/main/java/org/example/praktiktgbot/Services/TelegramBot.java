package org.example.praktiktgbot.Services;
import org.example.praktiktgbot.Entity.TelegramProperties;
import org.example.praktiktgbot.Entity.WebhookProperties;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@Service
public class TelegramBot extends SpringWebhookBot {

    private final TelegramProperties telegramProperties;

    private final WebhookProperties webhookProperties;

    private final GptService gptService;



    public TelegramBot(TelegramProperties telegramProperties,
                       WebhookProperties webhookProperties,
                       SetWebhook setWebhook,
                       GptService gptService) {
        super(setWebhook, telegramProperties.getToken());
        this.telegramProperties = telegramProperties;
        this.webhookProperties = webhookProperties;
        this.gptService = gptService;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return new SendMessage(update.getMessage().getChatId().toString(), gptService.answer(update.getMessage().getText()));
    }

    @Override
    public String getBotUsername() {
        return telegramProperties.getUsername();
    }

    @Override
    public String getBotPath() {
        return webhookProperties.getPath();
    }

}