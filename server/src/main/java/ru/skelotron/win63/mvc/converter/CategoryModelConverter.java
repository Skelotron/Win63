package ru.skelotron.win63.mvc.converter;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import ru.skelotron.win63.mvc.controller.CategoryController;
import ru.skelotron.win63.mvc.model.CategoryModel;
import ru.skelotron.win63.entity.CategoryEntity;
import ru.skelotron.win63.exception.EntityNotFoundException;
import ru.skelotron.win63.repository.CategoryRepository;

@Component
public class CategoryModelConverter extends RepresentationModelAssemblerSupport<CategoryEntity, CategoryModel> implements ModelConverter<CategoryEntity, CategoryModel> {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryModelConverter(CategoryRepository categoryRepository) {
        super(CategoryController.class, CategoryModel.class);
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryEntity convertToEntity(CategoryModel model) {
        return categoryRepository.findById(model.getId()).orElseThrow(() -> new EntityNotFoundException(CategoryEntity.class, model.getId()));
    }

    @Override
    @NotNull
    public CategoryModel toModel(CategoryEntity entity) {
        CategoryModel category = new CategoryModel();
        category.setId(entity.getId());
        category.setName(entity.getName());
        return category;
    }
}
