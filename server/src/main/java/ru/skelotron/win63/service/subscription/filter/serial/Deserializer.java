package ru.skelotron.win63.service.subscription.filter.serial;

public interface Deserializer<T> {
    T deserialize(String value);
}
