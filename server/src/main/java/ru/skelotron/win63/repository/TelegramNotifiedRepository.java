package ru.skelotron.win63.repository;

import ru.skelotron.win63.entity.TelegramNotified;

public interface TelegramNotifiedRepository extends NotifiedRepository<TelegramNotified> {
    TelegramNotified findByUserName(String userName);
}
