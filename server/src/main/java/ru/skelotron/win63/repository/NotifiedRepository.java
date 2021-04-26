package ru.skelotron.win63.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skelotron.win63.entity.Notified;

public interface NotifiedRepository<T extends Notified> extends JpaRepository<T, Long> {
}
