package ru.skelotron.win63.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skelotron.win63.mvc.converter.ItemSynchronizationModelConverter;
import ru.skelotron.win63.mvc.model.ItemSynchronizationModel;
import ru.skelotron.win63.entity.CategoryEntity;
import ru.skelotron.win63.entity.ItemSynchronizationEntity;
import ru.skelotron.win63.exception.EntityNotFoundException;
import ru.skelotron.win63.repository.CategoryRepository;
import ru.skelotron.win63.repository.ItemSynchronizationRepository;
import ru.skelotron.win63.service.item.ItemSynchronizationService;

import java.util.*;

@RestController
@RequestMapping("/synchronization/item")
public class ItemSynchronizationController extends AbstractController<ItemSynchronizationModelConverter, ItemSynchronizationRepository, ItemSynchronizationEntity, ItemSynchronizationModel> {
    private final CategoryRepository categoryRepository;
    private final ItemSynchronizationService itemSynchronizationService;

    @Autowired
    public ItemSynchronizationController(ItemSynchronizationRepository itemSynchronizationRepository, ItemSynchronizationModelConverter itemSynchronizationModelConverter, CategoryRepository categoryRepository, ItemSynchronizationService itemSynchronizationService) {
        super(itemSynchronizationModelConverter, itemSynchronizationRepository);
        this.categoryRepository = categoryRepository;
        this.itemSynchronizationService = itemSynchronizationService;
    }

    @GetMapping("/")
    public ResponseEntity<CollectionModel<ItemSynchronizationModel>> getSynchronizations(@PathVariable String entityName, @RequestParam(name = "last", required = false, defaultValue = "false") boolean onlyLast) {
        List<ItemSynchronizationEntity> synchronizationHistory;
        if (onlyLast) {
            synchronizationHistory = getRepository().findLastByTypeOrderBySyncDate(entityName);

            Collection<CategoryEntity> categories = new HashSet<>();
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
           synchronizationHistory = getRepository().findByTypeOrderBySyncDate(entityName);
        }

        return ResponseEntity.ok( convertToHolder(synchronizationHistory) );
    }

    @PostMapping("/category/{categoryId}")
    public ResponseEntity<Void> synchronize(@PathVariable String entityName, @PathVariable Long categoryId) {
        CategoryEntity category = categoryRepository.findById(categoryId).orElseThrow(() -> new EntityNotFoundException(CategoryEntity.class, categoryId));
        itemSynchronizationService.synchronize(category, true);
        return ResponseEntity.ok().build();
    }
}
