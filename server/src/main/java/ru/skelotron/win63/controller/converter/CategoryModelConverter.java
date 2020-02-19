package ru.skelotron.win63.controller.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skelotron.win63.controller.model.CategoryModel;
import ru.skelotron.win63.entity.CategoryEntity;
import ru.skelotron.win63.exception.EntityNotFoundException;
import ru.skelotron.win63.repository.CategoryRepository;

@Component
public class CategoryModelConverter implements ModelConverter<CategoryEntity, CategoryModel> {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryModelConverter(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryModel convertToModel(CategoryEntity entity) {
        CategoryModel category = new CategoryModel();
        category.setId(entity.getId());
        category.setName(entity.getName());
        return category;
    }

    @Override
    public CategoryEntity convertToEntity(CategoryModel model) {
        return categoryRepository.findById(model.getId()).orElseThrow(() -> new EntityNotFoundException(CategoryEntity.class, model.getId()));
    }
}
