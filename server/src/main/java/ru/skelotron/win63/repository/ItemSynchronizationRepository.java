package ru.skelotron.win63.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skelotron.win63.entity.ItemSynchronizationEntity;

import java.util.List;

public interface ItemSynchronizationRepository extends JpaRepository<ItemSynchronizationEntity, Long> {

    List<ItemSynchronizationEntity> findByTypeOrderBySyncDate(String entityName);

    @Query("select sync from ItemSynchronizationEntity sync " +
            "where (sync.category, sync.syncDate) in (" +
                "select e.category, max(e.syncDate) from ItemSynchronizationEntity e group by e.category" +
            ") ")
    List<ItemSynchronizationEntity> findLastByTypeOrderBySyncDate(String entityName);
}
