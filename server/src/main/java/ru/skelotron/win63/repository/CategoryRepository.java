package ru.skelotron.win63.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skelotron.win63.entity.CategoryEntity;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {
    CategoryEntity findByUrl(String url);

    CategoryEntity findByExternalId(String externalId);

    Iterable<CategoryEntity> findAllByOrderByName();
}
