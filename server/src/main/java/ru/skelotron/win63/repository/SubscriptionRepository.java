package ru.skelotron.win63.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.skelotron.win63.entity.CategoryEntity;
import ru.skelotron.win63.entity.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long>, PagingAndSortingRepository<Subscription, Long> {

    Subscription findByCategory(CategoryEntity category);
}
