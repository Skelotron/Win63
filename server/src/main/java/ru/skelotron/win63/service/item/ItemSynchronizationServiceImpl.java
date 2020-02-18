package ru.skelotron.win63.service.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skelotron.win63.entity.entity.CategoryEntity;
import ru.skelotron.win63.entity.entity.Subscription;
import ru.skelotron.win63.model.ItemsChangeData;
import ru.skelotron.win63.service.subscription.SubscriptionService;

import java.util.List;
import java.util.Map;

@Component
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
        for (Map.Entry<CategoryEntity, List<Subscription>> entry : categories.entrySet()) {
            ItemsChangeData changeData = itemService.load(entry.getKey());
            subscriptionService.fireItemsChangedEvent(changeData, entry.getValue());
        }
    }
}
