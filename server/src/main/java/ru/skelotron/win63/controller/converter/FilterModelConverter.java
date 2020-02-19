package ru.skelotron.win63.controller.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skelotron.win63.controller.model.FilterModel;
import ru.skelotron.win63.entity.Filter;
import ru.skelotron.win63.entity.Item;
import ru.skelotron.win63.repository.FilterRepository;

@Component
public class FilterModelConverter implements ModelConverter<Filter, FilterModel> {
    private final FilterRepository filterRepository;

    @Autowired
    public FilterModelConverter(FilterRepository filterRepository) {
        this.filterRepository = filterRepository;
    }

    @Override
    public FilterModel convertToModel(Filter entity) {
        FilterModel filter = new FilterModel();
        filter.setId(entity.getId());
        filter.setField(entity.getField());
        filter.setRelation(entity.getType());
        filter.setValue(entity.getValue());
        return filter;
    }

    @Override
    public Filter convertToEntity(FilterModel model) {
        Filter filter;
        if (model.getId() != null) {
            filter = filterRepository.findById(model.getId()).orElseThrow(() -> new IllegalArgumentException("Can't find Filter with id = " + model.getId()));
        } else {
            filter = new Filter();
        }
        filter.setField(model.getField());
        filter.setType(model.getRelation());
        filter.setEntity(Item.ENTITY_NAME);
        filter.setValue(model.getValue());
        return filter;
    }
}
