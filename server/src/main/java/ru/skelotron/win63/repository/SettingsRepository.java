package ru.skelotron.win63.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skelotron.win63.entity.Settings;

public interface SettingsRepository extends JpaRepository<Settings, Long> {
    Settings findByName(String name);
}
