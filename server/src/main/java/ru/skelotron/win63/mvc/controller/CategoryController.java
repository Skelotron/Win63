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
import ru.skelotron.win63.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryModelConverter categoryModelConverter;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository, CategoryModelConverter categoryModelConverter) {
        this.categoryRepository = categoryRepository;
        this.categoryModelConverter = categoryModelConverter;
    }

    @GetMapping("/")
    public ResponseEntity<Categories> getAll() {
        Iterable<CategoryEntity> categories = categoryRepository.findAll();

        List<CategoryModel> categoryModels = new ArrayList<>();

        for (CategoryEntity category : categories) {
            categoryModels.add( categoryModelConverter.convertToModel(category) );
        }

        return ResponseEntity.ok(new Categories(categoryModels));
    }
}
