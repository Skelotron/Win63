package ru.skelotron.win63.service.subscription.filter.checker;

import org.junit.Assert;
import org.junit.Test;
import ru.skelotron.win63.entity.CategoryEntity;
import ru.skelotron.win63.entity.Filter;
import ru.skelotron.win63.entity.FilterRelationType;
import ru.skelotron.win63.entity.Item;
import ru.skelotron.win63.service.subscription.filter.field.ItemFilterField;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;

public class ItemFilterCheckerTest {
    private final ItemFilterChecker checker = new ItemFilterChecker();

    @Test
    public void testContains() {
        Filter titleFilter = new Filter(FilterRelationType.CONTAINS, "Item", ItemFilterField.TITLE.name(), "Part of Title");
        Filter categoryFilter = new Filter(FilterRelationType.CONTAINS, "Item", ItemFilterField.CATEGORY.name(), "1|2|3");
        Item item = createItem();
        Assert.assertTrue(checker.check(titleFilter, item));
        Assert.assertTrue(checker.check(categoryFilter, item));
    }

    @Test
    public void testNotContains() {
        Filter titleFilter = new Filter(FilterRelationType.NOT_CONTAINS, "Item", ItemFilterField.TITLE.name(), "Spring");
        Filter categoryFilter = new Filter(FilterRelationType.NOT_CONTAINS, "Item", ItemFilterField.CATEGORY.name(), "2|3");
        Item item = createItem();
        Assert.assertTrue(checker.check(titleFilter, item));
        Assert.assertTrue(checker.check(categoryFilter, item));
    }

    @Test
    public void testEquals() {
        Filter priceFilter = new Filter(FilterRelationType.EQUALS, "Item", ItemFilterField.PRICE.name(), "1000");
        Filter titleFilter = new Filter(FilterRelationType.EQUALS, "Item", ItemFilterField.TITLE.name(), "This is valid Part of Title.");
        Filter categoryFilter = new Filter(FilterRelationType.NOT_CONTAINS, "Item", ItemFilterField.CATEGORY.name(), "2|3");
        Item item = createItem();
        Assert.assertTrue(checker.check(priceFilter, item));
        Assert.assertTrue(checker.check(titleFilter, item));
        Assert.assertTrue(checker.check(categoryFilter, item));
    }

    @Test
    public void testGreater() {
        Filter filter = new Filter(FilterRelationType.GREATER, "Item", ItemFilterField.PRICE.name(), "100");
        Item item = createItem();
        Assert.assertTrue(checker.check(filter, item));
    }

    @Test
    public void testGreaterOrEquals() {
        Filter equalsFilter = new Filter(FilterRelationType.GREATER_OR_EQUALS, "Item", ItemFilterField.PRICE.name(), "1000");
        Filter greaterFilter = new Filter(FilterRelationType.GREATER_OR_EQUALS, "Item", ItemFilterField.PRICE.name(), "100");
        Item item = createItem();
        Assert.assertTrue(checker.check(equalsFilter, item));
        Assert.assertTrue(checker.check(greaterFilter, item));
    }

    @Test
    public void testLesser() {
        Filter filter = new Filter(FilterRelationType.LESSER, "Item", ItemFilterField.PRICE.name(), "10000");
        Item item = createItem();
        Assert.assertTrue(checker.check(filter, item));
    }

    @Test
    public void testLesserOrEquals() {
        Filter equalsFilter = new Filter(FilterRelationType.LESSER_OR_EQUALS, "Item", ItemFilterField.PRICE.name(), "1000");
        Filter lesserFilter = new Filter(FilterRelationType.LESSER_OR_EQUALS, "Item", ItemFilterField.PRICE.name(), "10000");
        Item item = createItem();
        Assert.assertTrue(checker.check(equalsFilter, item));
        Assert.assertTrue(checker.check(lesserFilter, item));
    }

    private Item createItem() {
        CategoryEntity categoryEntity = new CategoryEntity("Test Category", "http://www.google.com", "123");
        categoryEntity.setId(1L);
        return new Item("http://www.google.com", "This is valid Part of Title.", BigDecimal.valueOf(1000), new Date(), categoryEntity, Collections.emptySet());
    }
}