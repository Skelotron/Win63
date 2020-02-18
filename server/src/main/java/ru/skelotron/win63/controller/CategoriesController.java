package ru.skelotron.win63.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skelotron.win63.entity.entity.CategoryEntity;
import ru.skelotron.win63.record.Categories;
import ru.skelotron.win63.record.CategoryRecord;
import ru.skelotron.win63.repository.CategoryRepository;
import ru.skelotron.win63.repository.SubscriptionRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoriesController {

    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/")
    public ResponseEntity<Categories> getAll() {
        Iterable<CategoryEntity> categories = categoryRepository.findAll();

        List<CategoryRecord> categoryRecords = new ArrayList<>();

        for (CategoryEntity category : categories) {
            categoryRecords.add( new CategoryRecord( category.getExternalId(), category.getName() ) );
        }

        return ResponseEntity.ok(new Categories(categoryRecords));
    }
}
