package ru.skelotron.win63.converter.impl;

import org.springframework.stereotype.Component;
import ru.skelotron.win63.converter.CategoryConverter;
import ru.skelotron.win63.entity.CategoryEntity;
import ru.skelotron.win63.http_entities.Category;

@Component
public class Win63CategoryConverter implements CategoryConverter<Category> {
    @Override
    public CategoryEntity convertToEntity(Category record) {
        return new CategoryEntity(record.getTitle(), record.getUrl(), record.getId());
    }
}
