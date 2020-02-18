package ru.skelotron.win63.service.subscription;

import ru.skelotron.win63.entity.Item;
import ru.skelotron.win63.entity.Notified;

import java.util.List;
import java.util.Set;

public interface NotificationService {
    void sendNotifications(Set<Notified> notifiedEntities, List<Item> matchedItems);
}
