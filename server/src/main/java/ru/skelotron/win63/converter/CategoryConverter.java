package ru.skelotron.win63.converter;

import org.springframework.stereotype.Component;
import ru.skelotron.win63.entity.CategoryEntity;
import ru.skelotron.win63.http_entities.Category;

@Component
public class CategoryConverter implements RecordConverter<Category, CategoryEntity> {
    @Override
    public CategoryEntity convertToEntity(Category record) {
        return new CategoryEntity(record.getTitle(), record.getUrl(), record.getId());
    }
}
