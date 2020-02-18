package ru.skelotron.win63.common;

import ru.skelotron.win63.entity.entity.Item;

public class ItemTagProcessor {
    public static String get(Tag tag, Item item) {
        switch (tag) {
            case ITEM_TITLE:
                return item.getTitle();

            case ITEM_COST:
                return String.valueOf(item.getAmount());

            case ITEM_URL:
                return item.getUrl();

            default:
                return "";
        }
    }
}
