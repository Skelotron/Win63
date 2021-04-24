package ru.skelotron.win63.service.subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skelotron.win63.common.NotificationSender;
import ru.skelotron.win63.entity.NotificationType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationSenderFactory {
    private final Map<NotificationType, NotificationSender> notificationSenders = new HashMap<>();

    @Autowired
    public void setNotificationSenders(@SuppressWarnings("TypeMayBeWeakened") List<NotificationSender> notificationSenders) {
        for (NotificationSender sender : notificationSenders) {
            this.notificationSenders.put(sender.getType(), sender);
        }
    }

    public NotificationSender get(NotificationType type) {
        NotificationSender notificationSender = notificationSenders.get(type);
        if (notificationSender == null) {
            throw new IllegalArgumentException("Unknown NotificationType: " + type);
        }
        return notificationSender;
    }
}
