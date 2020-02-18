package ru.skelotron.win63.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class CollectionUtil {
    public static <T> Map<String, T> toMap(Function<T, String> keyFunction, Iterable<T> values) {
        Map<String, T> map = new HashMap<>();
        for (T value : values) {
            map.put(keyFunction.apply(value), value);
        }
        return map;
    }
}
