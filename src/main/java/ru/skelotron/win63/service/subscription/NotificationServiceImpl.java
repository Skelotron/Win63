package ru.skelotron.win63.service.subscription;

import org.springframework.stereotype.Component;
import ru.skelotron.win63.entity.Item;
import ru.skelotron.win63.entity.NotificationType;
import ru.skelotron.win63.entity.Notified;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class NotificationServiceImpl implements NotificationService {
    @Override
    public void sendNotifications(Set<Notified> notifiedEntities, List<Item> matchedItems) {
        Map<NotificationType, List<Notified>> groupedByType = notifiedEntities.stream().collect(Collectors.groupingBy(Notified::getNotificationType));
        // todo: get notification service for each type and send to recipients
    }
}
