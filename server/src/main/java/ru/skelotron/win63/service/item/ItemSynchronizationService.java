package ru.skelotron.win63.service.item;

import ru.skelotron.win63.entity.CategoryEntity;
import ru.skelotron.win63.model.ItemsChangeData;

public interface ItemSynchronizationService {
    void synchronize();

    ItemsChangeData synchronize(CategoryEntity category, boolean manual);
}
