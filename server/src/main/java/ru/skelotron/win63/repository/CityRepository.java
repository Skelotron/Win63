package ru.skelotron.win63.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skelotron.win63.entity.CityEntity;

public interface CityRepository extends CrudRepository<CityEntity, Long> {
    CityEntity findByExternalId(String externalId);
}
