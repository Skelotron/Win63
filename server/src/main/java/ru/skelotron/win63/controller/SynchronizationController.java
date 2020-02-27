package ru.skelotron.win63.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skelotron.win63.controller.converter.ItemSynchronizationModelConverter;
import ru.skelotron.win63.controller.model.ItemSynchronizationModel;
import ru.skelotron.win63.controller.model.Synchronizations;
import ru.skelotron.win63.entity.CategoryEntity;
import ru.skelotron.win63.entity.ItemSynchronizationEntity;
import ru.skelotron.win63.exception.EntityNotFoundException;
import ru.skelotron.win63.repository.CategoryRepository;
import ru.skelotron.win63.repository.ItemSynchronizationRepository;
import ru.skelotron.win63.service.item.ItemSynchronizationService;

import java.util.*;

@RestController
@RequestMapping("/synchronization")
public class SynchronizationController {
    private final ItemSynchronizationRepository itemSynchronizationRepository;
    private final ItemSynchronizationModelConverter itemSynchronizationModelConverter;
    private final CategoryRepository categoryRepository;
    private final ItemSynchronizationService itemSynchronizationService;

    @Autowired
    public SynchronizationController(ItemSynchronizationRepository itemSynchronizationRepository, ItemSynchronizationModelConverter itemSynchronizationModelConverter, CategoryRepository categoryRepository, ItemSynchronizationService itemSynchronizationService) {
        this.itemSynchronizationRepository = itemSynchronizationRepository;
        this.itemSynchronizationModelConverter = itemSynchronizationModelConverter;
        this.categoryRepository = categoryRepository;
        this.itemSynchronizationService = itemSynchronizationService;
    }

    @GetMapping("/{entityName}")
    public ResponseEntity<Synchronizations> getSynchronizations(@PathVariable String entityName, @RequestParam(name = "last", required = false, defaultValue = "false") boolean onlyLast) {
        List<ItemSynchronizationEntity> synchronizationHistory;
        if (onlyLast) {
            synchronizationHistory = itemSynchronizationRepository.findLastByTypeOrderBySyncDate(entityName);

            Set<CategoryEntity> categories = new HashSet<>();
            for (ItemSynchronizationEntity item : synchronizationHistory) {
                categories.add(item.getCategory());
            }

            for (CategoryEntity categoryEntity : categoryRepository.findAllByOrderByName()) {
                if (!categories.contains(categoryEntity)) {
                    ItemSynchronizationEntity emptyEntity = new ItemSynchronizationEntity();
                    emptyEntity.setCategory(categoryEntity);
                    synchronizationHistory.add(emptyEntity);
                }
            }
        } else {
           synchronizationHistory = itemSynchronizationRepository.findByTypeOrderBySyncDate(entityName);
        }

        List<ItemSynchronizationModel> synchronizationHistoryModels = new ArrayList<>();
        for (ItemSynchronizationEntity entity : synchronizationHistory) {
            synchronizationHistoryModels.add( itemSynchronizationModelConverter.convertToModel(entity) );
        }

        return ResponseEntity.ok( new Synchronizations(synchronizationHistoryModels) );
    }

    @PostMapping("/{entityName}/category/{categoryId}")
    public ResponseEntity<Void> synchronize(@PathVariable String entityName, @PathVariable Long categoryId) {
        CategoryEntity category = categoryRepository.findById(categoryId).orElseThrow(() -> new EntityNotFoundException(CategoryEntity.class, categoryId));
        itemSynchronizationService.synchronize(category, true);
        return ResponseEntity.ok().build();
    }
}
