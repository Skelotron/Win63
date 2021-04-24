package ru.skelotron.win63.service.subscription.filter.checker;

import ru.skelotron.win63.entity.Filter;

public interface FilterChecker<E> {
    /**
     * Checks whenever specified entity matches with the filter
     */
    boolean check(Filter filter, E entity);

    Class<E> getCheckerClass();
}
