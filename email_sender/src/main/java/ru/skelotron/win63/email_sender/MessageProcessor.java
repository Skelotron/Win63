package ru.skelotron.win63.email_sender;

import ru.skelotron.win63.common.ItemTagProcessor;
import ru.skelotron.win63.common.Tag;
import ru.skelotron.win63.entity.Item;

public class MessageProcessor {
    private final String template;

    public MessageProcessor(String template) {
        this.template = template;
    }

    public String process(Item item) {
        String message = template;
        for (Tag tag : Tag.values()) {
            String tagValue = tag.getTagValue();
            if (message.contains(tagValue)) {
                String value = ItemTagProcessor.get(tag, item);
                message = message.replace(tagValue, value);
            }
        }
        return message;
    }
}
