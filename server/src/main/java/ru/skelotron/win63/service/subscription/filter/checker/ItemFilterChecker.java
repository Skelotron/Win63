package ru.skelotron.win63.service.subscription.filter.checker;

import org.springframework.stereotype.Component;
import ru.skelotron.win63.entity.Filter;
import ru.skelotron.win63.entity.Item;
import ru.skelotron.win63.service.subscription.filter.field.FilterField;
import ru.skelotron.win63.service.subscription.filter.field.ItemFilterField;

@Component
public class ItemFilterChecker extends AbstractFilterChecker<Item> {

    @Override
    public boolean check(Filter filter, Item item) {
        FilterField<Item> filteredField = ItemFilterField.fromValue(filter.getField());
        if (!filteredField.getSupportedRelations().contains(filter.getType())) {
            throw new IllegalArgumentException("Unsupported relation type " + filter.getType() + " for field " + filteredField);
        }

        Object valueFromItem = filteredField.getFieldRetriever().apply(item);

        return check(filteredField, filter.getType(), filter.getValue(), valueFromItem);
    }
}
