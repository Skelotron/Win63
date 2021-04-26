package ru.skelotron.win63.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skelotron.win63.entity.CityEntity;

public interface CityRepository extends JpaRepository<CityEntity, Long> {
    CityEntity findByExternalId(String externalId);
}
