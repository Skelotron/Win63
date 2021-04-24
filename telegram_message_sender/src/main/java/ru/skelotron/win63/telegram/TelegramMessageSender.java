package ru.skelotron.win63.telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TelegramMessageSender {
    private final TelegramBot telegramBot;

    @Autowired
    public TelegramMessageSender(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @SuppressWarnings("unused") // used by message broker
    public void send(TelegramMessage message) {
        telegramBot.sendMessage(message.getChatId(), message.getText());
    }
}
