package ru.skelotron.win63.service.subscription.filter.serial;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class PrimaryKeyDeserializerTest {

    @Test
    public void deserializeTest() {
        Assert.assertEquals(Collections.singletonList(1L), new PrimaryKeyDeserializer().deserialize("1"));
        Assert.assertEquals(Arrays.asList(1L, 2L, 3L), new PrimaryKeyDeserializer().deserialize("1|2|3"));
    }

}