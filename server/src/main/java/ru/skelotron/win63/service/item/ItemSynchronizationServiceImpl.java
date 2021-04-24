package ru.skelotron.win63.service.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skelotron.win63.entity.CategoryEntity;
import ru.skelotron.win63.entity.ItemSynchronizationEntity;
import ru.skelotron.win63.entity.Subscription;
import ru.skelotron.win63.mvc.model.ItemsChangeData;
import ru.skelotron.win63.repository.ItemSynchronizationRepository;
import ru.skelotron.win63.service.subscription.SubscriptionService;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ItemSynchronizationServiceImpl implements ItemSynchronizationService {
    private final SubscriptionService subscriptionService;
    private final ItemService itemService;
    private final ItemSynchronizationRepository itemSynchronizationRepository;

    @Autowired
    public ItemSynchronizationServiceImpl(SubscriptionService subscriptionService, ItemService itemService, ItemSynchronizationRepository itemSynchronizationRepository) {
        this.subscriptionService = subscriptionService;
        this.itemService = itemService;
        this.itemSynchronizationRepository = itemSynchronizationRepository;
    }

    @Override
    public void synchronize() {
        Map<CategoryEntity, List<Subscription>> categories = subscriptionService.findUniqueSubscribedCategories();
        log.info("Found " + categories.size() + " subscribed categories");
        for (Map.Entry<CategoryEntity, List<Subscription>> entry : categories.entrySet()) {
            log.info("Start Process " + entry.getKey().getName() + " (" + entry.getKey().getUrl() + ") category");
            ItemsChangeData changeData = synchronize(entry.getKey(), false);
            log.info("Found " + changeData.getNewItems().size() + " new Items for " + entry.getKey().getName() + " category");
            subscriptionService.fireItemsChangedEvent(changeData, entry.getValue());
        }
    }

    @Override
    public ItemsChangeData synchronize(CategoryEntity category, boolean manual) {
        ItemsChangeData changeData = itemService.load(category);
        saveToHistory(category, manual, changeData);
        return changeData;
    }

    private void saveToHistory(CategoryEntity category, boolean manual, ItemsChangeData changeData) {
        ItemSynchronizationEntity itemSynchronization = new ItemSynchronizationEntity();
        itemSynchronization.setCategory(category);
        itemSynchronization.setNewEntitiesCount(changeData.getNewItems().size());
        itemSynchronization.setSyncDate(new Date());
        itemSynchronization.setManual(manual);
        itemSynchronizationRepository.save(itemSynchronization);
    }
}
