package ru.skelotron.win63.service.subscription;

import org.springframework.stereotype.Component;
import ru.skelotron.win63.common.NotificationSender;
import ru.skelotron.win63.entity.entity.Item;
import ru.skelotron.win63.entity.entity.NotificationType;
import ru.skelotron.win63.entity.entity.Notified;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class NotificationServiceImpl implements NotificationService {
    @Override
    public void sendNotifications(Set<Notified> notifiedEntities, List<Item> matchedItems) {
        Map<NotificationType, List<Notified>> groupedByType = notifiedEntities.stream().collect(Collectors.groupingBy(Notified::getNotificationType));
        for (Map.Entry<NotificationType, List<Notified>> entry : groupedByType.entrySet()) {
            NotificationSender notificationSender = NotificationSenderFactory.getInstance().get(entry.getKey());
            for (Notified notified : entry.getValue()) {
                notificationSender.send(notified, matchedItems);
            }
        }
    }
}
