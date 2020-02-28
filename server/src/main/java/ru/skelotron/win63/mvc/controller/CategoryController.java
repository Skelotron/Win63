package ru.skelotron.win63.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skelotron.win63.mvc.converter.CategoryModelConverter;
import ru.skelotron.win63.mvc.model.CategoryModel;
import ru.skelotron.win63.entity.CategoryEntity;
import ru.skelotron.win63.mvc.model.Categories;
import ru.skelotron.win63.mvc.model.ModelListHolder;
import ru.skelotron.win63.repository.CategoryRepository;

@RestController
@RequestMapping("/category")
public class CategoryController extends AbstractController<CategoryModelConverter, CategoryRepository, CategoryEntity, CategoryModel> {

    @Autowired
    public CategoryController(CategoryRepository categoryRepository, CategoryModelConverter categoryModelConverter) {
        super(categoryModelConverter, categoryRepository);
    }

    @GetMapping("/")
    public ResponseEntity<ModelListHolder<CategoryModel>> getAll() {
        return ResponseEntity.ok(getAllRecordsHolder());
    }

    @Override
    protected ModelListHolder<CategoryModel> createModelListHolder() {
        return new Categories();
    }
}
