package ru.skelotron.win63.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skelotron.win63.entity.CategoryEntity;
import ru.skelotron.win63.entity.ItemSynchronizationEntity;

import java.util.List;

public interface ItemSynchronizationRepository extends CrudRepository<ItemSynchronizationEntity, Long> {
    List<ItemSynchronizationEntity> findByCategoryAndTypeOrderBySyncDate(CategoryEntity category, String entityName);
}
