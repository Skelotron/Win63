package ru.skelotron.win63.service.subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.skelotron.win63.entity.entity.CategoryEntity;
import ru.skelotron.win63.entity.entity.Item;
import ru.skelotron.win63.entity.entity.Subscription;
import ru.skelotron.win63.model.ItemsChangeData;
import ru.skelotron.win63.repository.SubscriptionRepository;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class SubscriptionServiceImpl implements SubscriptionService {
    private final NotificationService notificationService;
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionServiceImpl(NotificationService notificationService, SubscriptionRepository subscriptionRepository) {
        this.notificationService = notificationService;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public void fireItemsChangedEvent(ItemsChangeData changeData, Iterable<Subscription> subscriptions) {
        for (Subscription subscription : subscriptions) {
            List<Item> matchedItems = getMatchedItems(subscription, changeData.getNewItems());
            if (!matchedItems.isEmpty()) {
                notificationService.sendNotifications(subscription.getNotifiedEntities(), matchedItems);
            }
        }
    }

    @Override
    @Transactional
    public Map<CategoryEntity, List<Subscription>> findUniqueSubscribedCategories() {
        Iterable<Subscription> subscriptions = subscriptionRepository.findAll();
        Map<CategoryEntity, List<Subscription>> groupedSubscriptions = new HashMap<>();
        for (Subscription subscription : subscriptions) {
            groupedSubscriptions
                    .computeIfAbsent(subscription.getCategory(), a -> new ArrayList<>())
                    .add(subscription);
        }
        return groupedSubscriptions;
    }

    private List<Item> getMatchedItems(Subscription subscription, List<Item> newItems) {
        return newItems.stream().filter(item -> Objects.equals(item.getCategory(), subscription.getCategory())).collect(Collectors.toList());
    }
}
