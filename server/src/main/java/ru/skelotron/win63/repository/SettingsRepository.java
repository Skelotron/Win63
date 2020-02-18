package ru.skelotron.win63.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skelotron.win63.entity.entity.Settings;

public interface SettingsRepository extends CrudRepository<Settings, Long> {
    Settings findByName(String pageSize);
}
