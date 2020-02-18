package ru.skelotron.win63.service.subscription.filter.serial;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PrimaryKeyDeserializer implements Deserializer<List<Long>> {
    @Override
    public List<Long> deserialize(String value) {
        if (value.contains("|")) {
            String[] ids = value.split("\\|");
            return Arrays.stream(ids).map(Long::valueOf).collect(Collectors.toList());
        }
        return Collections.singletonList(Long.parseLong(value));
    }
}
