package ru.skelotron.win63.service.subscription.filter.field;

import ru.skelotron.win63.entity.FilterRelationType;
import ru.skelotron.win63.entity.Item;
import ru.skelotron.win63.service.subscription.filter.serial.Deserializer;
import ru.skelotron.win63.service.subscription.filter.serial.NumberDeserializer;
import ru.skelotron.win63.service.subscription.filter.serial.PrimaryKeyDeserializer;
import ru.skelotron.win63.service.subscription.filter.serial.StringDeserializer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public enum ItemFilterField implements FilterField<Item> {
    TITLE(
            Arrays.asList(FilterRelationType.EQUALS, FilterRelationType.CONTAINS),
            StringDeserializer::new,
            Item::getTitle),
    DESCRIPTION(
            Arrays.asList(FilterRelationType.EQUALS, FilterRelationType.CONTAINS),
            StringDeserializer::new,
            Item::getTitle),
    PRICE(
            Arrays.asList(FilterRelationType.EQUALS, FilterRelationType.GREATER,
                    FilterRelationType.GREATER_OR_EQUALS, FilterRelationType.LESSER,
                    FilterRelationType.LESSER_OR_EQUALS
            ),
            NumberDeserializer::new,
            Item::getAmount),
    CATEGORY(
            Arrays.asList(FilterRelationType.EQUALS, FilterRelationType.CONTAINS),
            PrimaryKeyDeserializer::new,
            item -> Collections.singletonList(item.getCategory().getId()));

    private final List<FilterRelationType> supportedRelations;
    private final Supplier<Deserializer<?>> deserializer;
    private final Function<Item, Object> fieldRetriever;

    ItemFilterField(List<FilterRelationType> supportedRelations, Supplier<Deserializer<?>> deserializer, Function<Item, Object> fieldRetriever) {
        this.supportedRelations = supportedRelations;
        this.deserializer = deserializer;
        this.fieldRetriever = fieldRetriever;
    }

    public static ItemFilterField fromValue(String value) {
        for (ItemFilterField filterField : values()) {
            if (filterField.name().equals(value)) {
                return filterField;
            }
        }
        throw new IllegalArgumentException("Unknown filterField name");
    }

    public List<FilterRelationType> getSupportedRelations() {
        return supportedRelations;
    }

    @Override
    public Supplier<Deserializer<?>> getDeserializer() {
        return deserializer;
    }

    public Function<Item, Object> getFieldRetriever() {
        return fieldRetriever;
    }
}
