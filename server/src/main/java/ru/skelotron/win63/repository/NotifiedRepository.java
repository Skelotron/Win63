package ru.skelotron.win63.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skelotron.win63.entity.EmailNotified;

public interface NotifiedRepository extends CrudRepository<EmailNotified, Long> {
}