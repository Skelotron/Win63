package ru.skelotron.win63.service.subscription;

import org.springframework.stereotype.Component;
import ru.skelotron.win63.common.NotificationSender;
import ru.skelotron.win63.entity.Filter;
import ru.skelotron.win63.entity.Item;
import ru.skelotron.win63.entity.NotificationType;
import ru.skelotron.win63.entity.Notified;
import ru.skelotron.win63.service.subscription.filter.checker.FilterChecker;
import ru.skelotron.win63.service.subscription.filter.checker.FilterCheckerFactory;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class NotificationServiceImpl implements NotificationService {
    @Override
    public void sendNotifications(Set<Notified> notifiedEntities, List<Item> matchedItems) {
        Map<NotificationType, List<Notified>> groupedByType = notifiedEntities.stream().collect(Collectors.groupingBy(Notified::getNotificationType));
        for (Map.Entry<NotificationType, List<Notified>> entry : groupedByType.entrySet()) {
            NotificationSender notificationSender = NotificationSenderFactory.getInstance().get(entry.getKey());
            for (Notified notified : entry.getValue()) {
                Iterable<Item> filteredItems = filterItems(notified.getFilters(), matchedItems);
                notificationSender.send(notified, filteredItems);
            }
        }
    }

    private Iterable<Item> filterItems(Set<Filter> filters, List<Item> items) {
        FilterChecker<Item> filterChecker = FilterCheckerFactory.getInstance().get(Item.class);
        Set<Filter> itemFilters = getItemFilters(filters);
        Collection<Item> filteredItems = new HashSet<>();
        for (Item item : items) {
            boolean match = true;
            for (Filter filter : itemFilters) {
                if (!filterChecker.check(filter, item)) {
                    match = false;
                    break;
                }
            }
            if (match) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }

    private Set<Filter> getItemFilters(Set<Filter> filters) {
        return filters.stream().filter(filter -> Item.ENTITY_NAME.equals(filter.getEntity())).collect(Collectors.toSet());
    }
}
