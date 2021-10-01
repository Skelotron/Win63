package ru.skelotron.win63.config;

public interface RabbitQueueConfig {
    String getExchange();
    String getQueue();
    String getKey();
}
