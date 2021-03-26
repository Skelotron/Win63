package ru.skelotron.win63.mvc.model;

import lombok.Getter;
import ru.skelotron.win63.entity.Item;

import java.util.Collections;
import java.util.List;

@Getter
public class ItemsChangeData {
    private final List<Item> newItems;

    public ItemsChangeData(List<Item> newItems) {
        this.newItems = newItems;
    }

    public ItemsChangeData() {
        this(Collections.emptyList());
    }
}
