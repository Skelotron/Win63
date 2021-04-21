package ru.skelotron.win63.service.subscription.filter.checker;

import org.apache.commons.lang3.StringUtils;
import ru.skelotron.win63.entity.Entity;
import ru.skelotron.win63.entity.FilterRelationType;
import ru.skelotron.win63.service.subscription.filter.serial.Deserializer;
import ru.skelotron.win63.service.subscription.filter.field.FilterField;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;

public abstract class AbstractFilterChecker<E> implements FilterChecker<E> {
    protected  <T extends Entity> boolean check(FilterField<T> field, FilterRelationType relationType, String value, Object itemValue) {
        Deserializer<?> deserializer = field.getDeserializer().get();
        Object filterValue = deserializer.deserialize(value);
        return check(itemValue, filterValue, relationType);
    }

    private static boolean check(Object itemValue, Object filterValue, FilterRelationType relationType) {
        switch (relationType) {
            case EQUALS:
                return checkEquals(itemValue, filterValue);

            case LESSER:
                return getBigDecimal(itemValue).compareTo(getBigDecimal(filterValue)) < 0;
            case GREATER:
                return getBigDecimal(itemValue).compareTo(getBigDecimal(filterValue)) > 0;
            case GREATER_OR_EQUALS:
                return getBigDecimal(itemValue).compareTo(getBigDecimal(filterValue)) >= 0;
            case LESSER_OR_EQUALS:
                return getBigDecimal(itemValue).compareTo(getBigDecimal(filterValue)) <= 0;

            case CONTAINS:
                return checkContains(itemValue, filterValue);

            case NOT_CONTAINS:
                return checkNotContains(itemValue, filterValue);
            default:
                throw new IllegalArgumentException("Unsupported RelationType: " + relationType);
        }
    }

    private static boolean checkNotContains(Object itemValue, Object filterValue) {
        if (filterValue instanceof Collection) {
            Collection<?> filterValues = (Collection<?>) filterValue;
            Collection<?> itemValues = (Collection<?>) itemValue;
            for (Object value : itemValues) {
                if (filterValues.contains(value)) {
                    return false;
                }
            }
            return true;
        } else {
            return !StringUtils.containsIgnoreCase(String.valueOf(itemValue), String.valueOf(filterValue));
        }
    }

    private static boolean checkContains(Object itemValue, Object filterValue) {
        if (filterValue instanceof Collection) {
            return ((Collection<?>) filterValue).containsAll((Collection<?>) itemValue);
        } else {
            return StringUtils.containsIgnoreCase(String.valueOf(itemValue), String.valueOf(filterValue));
        }
    }

    private static boolean checkEquals(Object itemValue, Object filterValue) {
        if (itemValue instanceof Number) {
            Number itemNumber = (Number) itemValue;
            Number filterNumber = (Number) filterValue;
            if (itemNumber instanceof Double) {
                return itemNumber.doubleValue() == filterNumber.doubleValue();
            } else {
                return itemNumber.longValue() == filterNumber.longValue();
            }
        }
        if (itemValue instanceof String) {
            String itemString = String.valueOf(itemValue);
            String filterString = String.valueOf(filterValue);
            return (itemString == null && filterString == null) || itemString != null && itemString.equalsIgnoreCase(filterString);
        }
        if (itemValue instanceof Collection) {
            Collection<?> itemList = (Collection<?>) itemValue;
            Collection<?> filterList = (Collection<?>) filterValue;
            return itemList.containsAll(filterList) && filterList.containsAll(itemList);
        }
        return false;
    }

    private static BigDecimal getBigDecimal(Object value) {
        if (value instanceof Number) {
            return BigDecimal.valueOf(((Number) value).doubleValue()).setScale(2, RoundingMode.UP);
        }
        throw new IllegalArgumentException("Expected double value but found: " + value);
    }
}
