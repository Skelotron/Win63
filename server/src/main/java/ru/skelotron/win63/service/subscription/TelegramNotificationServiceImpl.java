package ru.skelotron.win63.service.subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skelotron.win63.entity.NotificationType;
import ru.skelotron.win63.entity.TelegramNotified;
import ru.skelotron.win63.repository.TelegramNotifiedRepository;
import ru.skelotron.win63.telegram.TelegramRegistrationService;

import java.util.Objects;

@Component
public class TelegramNotificationServiceImpl implements TelegramNotificationService, TelegramRegistrationService {
    private final TelegramNotifiedRepository notifiedRepository;

    @Autowired
    public TelegramNotificationServiceImpl(TelegramNotifiedRepository notifiedRepository) {
        this.notifiedRepository = notifiedRepository;
    }

    @Override
    public boolean registerUser(String userName, Long userId, Long chatId) {
        TelegramNotified notified = notifiedRepository.findByUserName(userName);
        if (notified == null || notified.getNotificationType() != NotificationType.TELEGRAM) {
            return false;
        }

        boolean newRegistration = !Objects.equals(userName, notified.getUserName())
                || !Objects.equals(chatId, notified.getChatId())
                || !Objects.equals(userId, notified.getUserId());

        notified.setChatId(chatId);
        notified.setUserId(userId);
        notifiedRepository.save(notified);

        return newRegistration;
    }
}
