package ru.skelotron.win63.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skelotron.win63.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    CategoryEntity findByUrl(String url);

    CategoryEntity findByExternalId(String externalId);

    Iterable<CategoryEntity> findAllByOrderByName();
}
