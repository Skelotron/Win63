package ru.skelotron.win63.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skelotron.win63.entity.Item;

public interface ItemRepository extends CrudRepository<Item, Long> {
    Item findByUrl(String url);
}
