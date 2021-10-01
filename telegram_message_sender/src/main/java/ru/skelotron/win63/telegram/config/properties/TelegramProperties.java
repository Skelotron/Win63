package ru.skelotron.win63.telegram.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.skelotron.win63.config.RabbitQueueConfig;

@Configuration
@ConfigurationProperties("rabbit.telegram")
public class TelegramProperties implements RabbitQueueConfig {
    private String exchange;
    private String queue;
    private String key;

    @Override
    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    @Override
    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    @Override
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
