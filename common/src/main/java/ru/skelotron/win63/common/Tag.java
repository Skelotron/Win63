package ru.skelotron.win63.common;

public enum Tag {
    ITEM_TITLE("ItemTitle"),
    ITEM_DESCRIPTION("ItemDescription"),
    ITEM_URL("ItemUrl"),
    ITEM_COST("ItemCost");

    private final String name;

    Tag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getTagValue() {
        return "<" + getName() + ">";
    }
}
