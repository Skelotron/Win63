package ru.skelotron.win63.service.subscription;

import email_sender.EmailSender;
import ru.skelotron.win63.common.NotificationSender;
import ru.skelotron.win63.entity.NotificationType;

public class NotificationSenderFactory {
    private static final NotificationSenderFactory INSTANCE = new NotificationSenderFactory();

    private NotificationSenderFactory() {
    }

    public static NotificationSenderFactory getInstance() {
        return INSTANCE;
    }

    public NotificationSender get(NotificationType type) {
        switch (type) {
            case EMAIL:
                return EmailSender.getInstance();

            default:
                throw new IllegalArgumentException("Unknown NotificationType");
        }
    }
}
