package ru.skelotron.win63.service.item;

import ru.skelotron.win63.entity.CategoryEntity;
import ru.skelotron.win63.model.ItemsChangeData;

public interface ItemService {
    ItemsChangeData load(CategoryEntity category);
}
