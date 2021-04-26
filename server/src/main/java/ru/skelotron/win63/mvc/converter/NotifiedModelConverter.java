package ru.skelotron.win63.mvc.converter;

import ru.skelotron.win63.entity.Filter;
import ru.skelotron.win63.entity.NotificationType;
import ru.skelotron.win63.entity.Notified;
import ru.skelotron.win63.mvc.model.FilterModel;
import ru.skelotron.win63.mvc.model.NotifiedModel;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class NotifiedModelConverter implements ModelConverter<Notified, NotifiedModel> {
    private final FilterModelConverter filterModelConverter;

    public NotifiedModelConverter(FilterModelConverter filterModelConverter) {
        this.filterModelConverter = filterModelConverter;
    }

    protected void convertToModel(NotifiedModel notified, Notified entity) {
        notified.setId(entity.getId());
        notified.setFilters(new ArrayList<>());
        for (Filter filter : entity.getFilters()) {
            notified.getFilters().add( filterModelConverter.convertToModel(filter) );
        }
    }

    protected void convertToEntity(NotifiedModel model, Notified notified) {
        Set<Filter> filters = new HashSet<>();
        for (FilterModel filterModel : model.getFilters()) {
            Filter filter = filterModelConverter.convertToEntity(filterModel);
            if (filter.getNotified() == null) {
                filter.setNotified(notified);
            }
            filters.add(filter);
        }
        if (notified.getId() != null) {
            mergeFilters(notified, filters);
        }
        else {
            notified.setFilters(filters);
            notified.setSubscriptions(new HashSet<>());
        }
    }

    private void mergeFilters(Notified notified, Collection<Filter> filters) {
        Iterator<Filter> filterIterator = notified.getFilters().iterator();
        Map<Long, Filter> filtersByIds = filters.stream().filter(f -> f.getId() != null).collect(Collectors.toMap(Filter::getId, Function.identity(), (f1, f2) -> f1));
        while (filterIterator.hasNext()) {
            Filter existingFilter = filterIterator.next();
            if (filtersByIds.containsKey(existingFilter.getId())) {
                mergeFilters(existingFilter, filtersByIds.get(existingFilter.getId()));
            }
            else {
                filterIterator.remove();
            }
        }
        notified.getFilters().addAll(filters.stream().filter(f -> f.getId() == null).collect(Collectors.toSet()));
    }

    private void mergeFilters(Filter existingFilter, Filter filter) {
        existingFilter.setField(filter.getField());
        existingFilter.setType(filter.getType());
        existingFilter.setValue(filter.getValue());
        existingFilter.setEntity(filter.getEntity());
    }

    protected abstract NotificationType getType();
}
