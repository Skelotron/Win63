package ru.skelotron.win63.common;

import ru.skelotron.win63.entity.entity.Item;
import ru.skelotron.win63.entity.entity.Notified;

public interface NotificationSender {
    void send(Notified notified, Iterable<Item> items);
}
