package ru.skelotron.win63.service.subscription.filter.checker;

import org.apache.commons.lang3.StringUtils;
import ru.skelotron.win63.entity.Entity;
import ru.skelotron.win63.entity.FilterRelationType;
import ru.skelotron.win63.service.subscription.filter.serial.Deserializer;
import ru.skelotron.win63.service.subscription.filter.field.FilterField;

import java.util.List;

public abstract class AbstractFilterChecker<E> implements FilterChecker<E> {
    protected  <T extends Entity> boolean compare(FilterField<T> field, FilterRelationType relationType, String value, Object itemValue) {
        Deserializer<?> deserializer = field.getDeserializer().get();
        Object filterValue = deserializer.deserialize(value);
        return compare(itemValue, filterValue, relationType);
    }

    private static boolean compare(Object itemValue, Object filterValue, FilterRelationType relationType) {
        switch (relationType) {
            case EQUALS:
                if (itemValue instanceof Number) {
                    Number itemNumber = (Number) itemValue;
                    Number filterNumber = (Number) filterValue;
                    if (itemNumber instanceof Double) {
                        return itemNumber.doubleValue() == filterNumber.doubleValue();
                    } else {
                        return itemNumber.longValue() == filterNumber.longValue();
                    }
                } else if (itemValue instanceof String) {
                    String itemString = String.valueOf(itemValue);
                    String filterString = String.valueOf(filterValue);
                    return (itemString == null && filterString == null) || itemString != null && itemString.equalsIgnoreCase(filterString);
                } else if (itemValue instanceof List) {
                    List itemList = (List) itemValue;
                    List filterList = (List) filterValue;
                    return itemList.containsAll(filterList) && filterList.containsAll(itemList);
                }
                return false;

            case LESSER:
                return getDouble(itemValue) < getDouble(filterValue);
            case GREATER:
                return getDouble(itemValue) > getDouble(filterValue);
            case GREATER_OR_EQUALS:
                return getDouble(itemValue) >= getDouble(filterValue);
            case LESSER_OR_EQUALS:
                return getDouble(itemValue) <= getDouble(filterValue);

            case CONTAINS:
                if (filterValue instanceof List) {
                    return ((List) itemValue).containsAll((List) filterValue);
                } else {
                    return StringUtils.containsIgnoreCase(String.valueOf(itemValue), String.valueOf(filterValue));
                }
            default:
                throw new IllegalArgumentException("Unsupported RelationType: " + relationType);
        }
    }

    private static double getDouble(Object value) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        throw new IllegalArgumentException("Expected double value but found: " + value);
    }
}
