package ru.skelotron.win63.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skelotron.win63.controller.converter.ItemSynchronizationModelConverter;
import ru.skelotron.win63.controller.model.ItemSynchronizationModel;
import ru.skelotron.win63.controller.model.Synchronizations;
import ru.skelotron.win63.entity.CategoryEntity;
import ru.skelotron.win63.entity.ItemSynchronizationEntity;
import ru.skelotron.win63.exception.EntityNotFoundException;
import ru.skelotron.win63.repository.CategoryRepository;
import ru.skelotron.win63.repository.ItemSynchronizationRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/synchronization")
public class SynchronizationController {
    private final ItemSynchronizationRepository itemSynchronizationRepository;
    private final ItemSynchronizationModelConverter itemSynchronizationModelConverter;
    private final CategoryRepository categoryRepository;

    @Autowired
    public SynchronizationController(ItemSynchronizationRepository itemSynchronizationRepository, ItemSynchronizationModelConverter itemSynchronizationModelConverter, CategoryRepository categoryRepository) {
        this.itemSynchronizationRepository = itemSynchronizationRepository;
        this.itemSynchronizationModelConverter = itemSynchronizationModelConverter;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/{entityName}/category/{categoryId}")
    public ResponseEntity<Synchronizations> getSynchronizations(@PathVariable String entityName, @PathVariable Long categoryId) {
        CategoryEntity category = categoryRepository.findById(categoryId).orElseThrow(() -> new EntityNotFoundException(CategoryEntity.class, categoryId));
        List<ItemSynchronizationEntity> synchronizationHistory = itemSynchronizationRepository.findByCategoryAndTypeOrderBySyncDate(category, entityName);

        List<ItemSynchronizationModel> synchronizationHistoryModels = new ArrayList<>();
        for (ItemSynchronizationEntity entity : synchronizationHistory) {
            synchronizationHistoryModels.add( itemSynchronizationModelConverter.convertToModel(entity) );
        }

        return ResponseEntity.ok( new Synchronizations(synchronizationHistoryModels) );
    }
}
