package ru.skelotron.win63.service.subscription.filter.checker;

import ru.skelotron.win63.entity.Filter;

public interface FilterChecker<E> {
    boolean check(Filter filter, E entity);
}
