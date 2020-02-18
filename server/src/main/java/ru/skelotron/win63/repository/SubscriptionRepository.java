package ru.skelotron.win63.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skelotron.win63.entity.CategoryEntity;
import ru.skelotron.win63.entity.Subscription;

public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {

    Subscription findByCategory(CategoryEntity category);
}
