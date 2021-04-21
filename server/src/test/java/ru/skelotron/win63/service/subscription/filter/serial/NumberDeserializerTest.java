package ru.skelotron.win63.service.subscription.filter.serial;


import org.junit.Assert;
import org.junit.Test;

public class NumberDeserializerTest {

    @Test
    public void deserializeTest() {
        Assert.assertEquals(123.0, new NumberDeserializer().deserialize("123"), 0.01);
    }
}