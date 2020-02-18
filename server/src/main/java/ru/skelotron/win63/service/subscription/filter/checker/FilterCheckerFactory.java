package ru.skelotron.win63.service.subscription.filter.checker;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class FilterCheckerFactory {
    private static final FilterCheckerFactory INSTANCE = new FilterCheckerFactory();
    private final Map<Class<?>, Supplier<FilterChecker<?>>> checkers;

    private FilterCheckerFactory() {
        this.checkers = new HashMap<>();
    }

    public static FilterCheckerFactory getInstance() {
        return INSTANCE;
    }

    public void register(Class<?> cls, Supplier<FilterChecker<?>> checker) {
        checkers.put(cls, checker);
    }

    @SuppressWarnings("unchecked")
    public <T> FilterChecker<T> get(Class<T> cls) {
        Supplier<FilterChecker<?>> constructor = checkers.get(cls);
        if (constructor == null) {
            throw new IllegalArgumentException("Unregistered entityType " + cls.getName());
        }
        return (FilterChecker<T>) constructor.get();
    }
}
