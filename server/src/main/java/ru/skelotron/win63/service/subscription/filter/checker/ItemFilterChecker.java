package ru.skelotron.win63.service.subscription.filter.checker;

import ru.skelotron.win63.entity.Filter;
import ru.skelotron.win63.entity.Item;
import ru.skelotron.win63.service.subscription.filter.field.FilterField;
import ru.skelotron.win63.service.subscription.filter.field.ItemFilterField;

public class ItemFilterChecker extends AbstractFilterChecker<Item> {
    private static final ItemFilterChecker INSTANCE = new ItemFilterChecker();

    private ItemFilterChecker() {
    }

    public static ItemFilterChecker getInstance() {
        return INSTANCE;
    }

    public boolean check(Filter filter, Item item) {
        FilterField<Item> filteredField = ItemFilterField.fromValue(filter.getField());
        if (!filteredField.getSupportedRelations().contains(filter.getType())) {
            throw new IllegalArgumentException("Unsupported relation type " + filter.getType() + " for field " + filteredField);
        }

        Object valueFromItem = filteredField.getFieldRetriever().apply(item);

        return compare(filteredField, filter.getType(), filter.getValue(), valueFromItem);
    }
}
