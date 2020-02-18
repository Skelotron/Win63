package ru.skelotron.win63.service.item;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skelotron.win63.entity.entity.CategoryEntity;
import ru.skelotron.win63.entity.entity.Subscription;
import ru.skelotron.win63.model.ItemsChangeData;
import ru.skelotron.win63.service.subscription.SubscriptionService;

import java.util.List;
import java.util.Map;

@Component
@Log
public class ItemSynchronizationServiceImpl implements ItemSynchronizationService {
    private final SubscriptionService subscriptionService;
    private final ItemService itemService;

    @Autowired
    public ItemSynchronizationServiceImpl(SubscriptionService subscriptionService, ItemService itemService) {
        this.subscriptionService = subscriptionService;
        this.itemService = itemService;
    }

    @Override
    public void synchronize() {
        Map<CategoryEntity, List<Subscription>> categories = subscriptionService.findUniqueSubscribedCategories();
        log.info("Found " + categories.size() + " subscribed categories");
        for (Map.Entry<CategoryEntity, List<Subscription>> entry : categories.entrySet()) {
            log.info("Start Process " + entry.getKey().getName() + " (" + entry.getKey().getUrl() + ") category");
            ItemsChangeData changeData = itemService.load(entry.getKey());
            log.info("Found " + changeData.getNewItems().size() + " new Items for " + entry.getKey().getName() + " category");
            subscriptionService.fireItemsChangedEvent(changeData, entry.getValue());
        }
    }
}
