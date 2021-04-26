package ru.skelotron.win63.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skelotron.win63.entity.Filter;

public interface FilterRepository extends JpaRepository<Filter, Long> {
}
