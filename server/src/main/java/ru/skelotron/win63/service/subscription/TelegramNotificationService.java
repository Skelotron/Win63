package ru.skelotron.win63.service.subscription;

public interface TelegramNotificationService {
    boolean registerUser(String userName, Long userId, Long chatId);
}
