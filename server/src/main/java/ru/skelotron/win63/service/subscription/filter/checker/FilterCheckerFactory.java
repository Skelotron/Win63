package ru.skelotron.win63.service.subscription.filter.checker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FilterCheckerFactory {
    private final Map<Class<?>, FilterChecker<?>> checkers = new HashMap<>();

    @Autowired
    public void setCheckers(@SuppressWarnings("TypeMayBeWeakened") List<FilterChecker<?>> checkers) {
        for (FilterChecker<?> checker : checkers) {
            this.checkers.put(checker.getCheckerClass(), checker);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> FilterChecker<T> get(Class<T> cls) {
        FilterChecker<?> filterChecker = checkers.get(cls);
        if (filterChecker == null) {
            throw new IllegalArgumentException("Unregistered entityType " + cls.getName());
        }
        return (FilterChecker<T>) filterChecker;
    }
}
