package ru.skelotron.win63.mvc.converter;

import ru.skelotron.win63.entity.Filter;
import ru.skelotron.win63.entity.NotificationType;
import ru.skelotron.win63.entity.Notified;
import ru.skelotron.win63.mvc.model.FilterModel;
import ru.skelotron.win63.mvc.model.NotifiedModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
        notified.setFilters(filters);
    }

    protected abstract NotificationType getType();
}
