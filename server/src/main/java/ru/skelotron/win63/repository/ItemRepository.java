package ru.skelotron.win63.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skelotron.win63.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByUrl(String url);
}
