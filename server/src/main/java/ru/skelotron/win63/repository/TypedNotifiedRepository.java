package ru.skelotron.win63.repository;

import ru.skelotron.win63.entity.NotificationType;

public interface TypedNotifiedRepository {
    NotificationType getType();
}
