package ru.skelotron.win63.telegram;

public interface TelegramRegistrationService {
    boolean registerUser(String username, Long userId, Long chatId);
}
