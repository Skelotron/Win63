package ru.skelotron.win63.common;

import ru.skelotron.win63.entity.Item;
import ru.skelotron.win63.entity.Notified;

public interface NotificationSender {
    void send(Notified notified, Iterable<Item> items);
}
