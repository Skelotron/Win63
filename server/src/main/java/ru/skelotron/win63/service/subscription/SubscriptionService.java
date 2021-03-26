package ru.skelotron.win63.service.subscription;

import ru.skelotron.win63.entity.CategoryEntity;
import ru.skelotron.win63.entity.Subscription;
import ru.skelotron.win63.mvc.model.ItemsChangeData;

import java.util.List;
import java.util.Map;

public interface SubscriptionService {
    void fireItemsChangedEvent(ItemsChangeData changeData, Iterable<Subscription> subscriptions);

    Map<CategoryEntity, List<Subscription>> findUniqueSubscribedCategories();
}
