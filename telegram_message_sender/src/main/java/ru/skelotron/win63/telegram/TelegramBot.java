package ru.skelotron.win63.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.skelotron.win63.common.Localization;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private final TelegramRegistrationService registrationService;
    private final Localization localization;
    private final String botUsername;
    private final String botToken;

    public TelegramBot(TelegramRegistrationService registrationService, Localization localization,
                       @Value("${telegram.api.bot.name}") String botUsername, @Value("${telegram.api.bot.token}") String botToken) {
        this.registrationService = registrationService;
        this.localization = localization;
        this.botUsername = botUsername;
        this.botToken = botToken;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        User user = message.getFrom();
        if (user.getUserName() != null) { // support only users with username
            boolean newRegistration = registrationService.registerUser(user.getUserName(), user.getId(), message.getChatId());
            if (newRegistration) {
                log.info("Registered User: {}, userId = {}, chatId = {}", user.getUserName(), user.getId(), message.getChatId());
                responseNewRegistration(message);
            }
        }
    }

    private void responseNewRegistration(Message message) {
        sendMessage(message.getChatId(), localization.getMessage("telegram.registration.success"));
    }

    public void sendMessage(Long chatId, String text) {
        try {
            execute(new SendMessage(String.valueOf(chatId), text));
        } catch (TelegramApiException e) {
            log.error("Error on send message to: " + chatId, e);
        }
    }
}
