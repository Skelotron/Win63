package ru.skelotron.win63.email_sender;

import org.junit.Assert;
import org.junit.Test;
import ru.skelotron.win63.common.MessageProcessor;
import ru.skelotron.win63.entity.CategoryEntity;
import ru.skelotron.win63.entity.Item;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;

public class MessageProcessorTest {

    @Test
    public void process() {
        Assert.assertEquals("", new MessageProcessor("").process(createItem()));
        Assert.assertEquals("Simple Text", new MessageProcessor("Simple Text").process(createItem()));

        String template = "New item with name <ItemTitle> [<ItemCost>] is available at <ItemUrl>";
        String expectedMessage = "New item with name Item 123 [10000] is available at https://www.google.com/category/123/item/123";
        Assert.assertEquals(expectedMessage, new MessageProcessor(template).process(createItem()));
    }

    private Item createItem() {
        return new Item("https://www.google.com/category/123/item/123", "Item 123", BigDecimal.valueOf(10000), new Date(), createCategory(), Collections.emptySet());
    }

    private CategoryEntity createCategory() {
        return new CategoryEntity("Category 123", "https://www.google.com/category/123", "123456");
    }
}