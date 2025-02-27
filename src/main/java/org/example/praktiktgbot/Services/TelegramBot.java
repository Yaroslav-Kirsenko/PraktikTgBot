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

    private final UserRequestService userRequestService;

    public TelegramBot(TelegramProperties telegramProperties,
                       WebhookProperties webhookProperties,
                       SetWebhook setWebhook,
                       GptService gptService, UserRequestService userRequestService) {
        super(setWebhook, telegramProperties.getToken());
        this.telegramProperties = telegramProperties;
        this.webhookProperties = webhookProperties;
        this.gptService = gptService;
        this.userRequestService = userRequestService;
    }

//    @Override
//    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
//
//        Long chatId = update.getMessage().getChatId();
//        String userMessage = update.getMessage().getText();
//
//        String botResponse;
//
//        if (userMessage.equals("/history")) {
//            botResponse = userRequestService.getUserHistory(chatId);
//        } else {
//            botResponse = gptService.answer(userMessage);
//            userRequestService.saveRequest(chatId, userMessage, botResponse);
//        }
//
//        SendMessage message = new SendMessage();
//        message.setChatId(chatId.toString());
//        message.setText(botResponse);
//        message.setParseMode("Markdown");
//
//        return message;
//    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        if (update.getMessage() == null || update.getMessage().getText() == null) {
            return null; // Игнорируем обновления без сообщений
        }

        Long chatId = update.getMessage().getChatId();
        String userMessage = update.getMessage().getText();
        String botResponse;

        if (userMessage.equals("/history")) {
            botResponse = userRequestService.getUserHistory(chatId);
            System.out.println("История для " + chatId + ": " + botResponse); // Отладка
        } else {
            botResponse = gptService.answer(userMessage);
            String savedMessage = userMessage.length() > 255 ? userMessage.substring(0, 255) : userMessage;
            String savedResponse = botResponse.length() > 255 ? botResponse.substring(0, 255) : botResponse;
            userRequestService.saveRequest(chatId, savedMessage, savedResponse);
        }

        if (botResponse == null || botResponse.isEmpty()) {
            botResponse = "История пуста или недоступна.";
        }

        SendMessage message = new SendMessage(chatId.toString(), botResponse);
        message.setParseMode("Markdown");

        return message;
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