package ru.skelotron.win63.mvc.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skelotron.win63.mvc.model.FilterModel;
import ru.skelotron.win63.entity.Filter;
import ru.skelotron.win63.entity.Item;
import ru.skelotron.win63.exception.EntityNotFoundException;
import ru.skelotron.win63.repository.FilterRepository;
import ru.skelotron.win63.service.subscription.filter.field.ItemFilterField;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

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
        Object value;
        if (ItemFilterField.CATEGORY.name().equals(entity.getField())) {
            value = Arrays.stream(entity.getValue().split("\\|")).map(Long::valueOf).collect(Collectors.toList());
        } else {
            value = entity.getValue();
        }
        filter.setValue(value);
        return filter;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Filter convertToEntity(FilterModel model) {
        Filter filter;
        if (model.getId() != null) {
            filter = filterRepository.findById(model.getId()).orElseThrow(() -> new EntityNotFoundException(Filter.class, model.getId()));
        } else {
            filter = new Filter();
        }
        filter.setField(model.getField());
        filter.setType(model.getRelation());
        filter.setEntity(Item.ENTITY_NAME);

        String value;
        if (ItemFilterField.CATEGORY.name().equals(model.getField())) {
            value = ((Collection<Long>) model.getValue()).stream().map(String::valueOf).collect(Collectors.joining("|"));
        } else {
            value = String.valueOf(model.getValue());
        }
        filter.setValue(value);
        return filter;
    }
}
