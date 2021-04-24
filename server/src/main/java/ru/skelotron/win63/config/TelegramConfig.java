package ru.skelotron.win63.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.skelotron.win63.telegram.TelegramMessagePublisher;
import ru.skelotron.win63.telegram.TelegramMessageSender;

@Configuration
@SuppressWarnings("MethodMayBeStatic")
public class TelegramConfig {
    @Bean
    public Queue telegramQueue() {
        return new Queue(TelegramMessagePublisher.QUEUE_NAME, false);
    }

    @Bean
    public TopicExchange telegramTopicExchange() {
        return new TopicExchange("telegram-exchange");
    }

    @Bean
    public Binding telegramBinding(Queue telegramQueue, TopicExchange telegramTopicExchange) {
        return BindingBuilder.bind(telegramQueue).to(telegramTopicExchange).with("");
    }

    @Bean
    public SimpleMessageListenerContainer telegramContainer(ConnectionFactory connectionFactory, MessageListenerAdapter telegramListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(TelegramMessagePublisher.QUEUE_NAME);
        container.setMessageListener(telegramListenerAdapter);
        return container;
    }

    @Bean
    public MessageListenerAdapter telegramListenerAdapter(TelegramMessageSender telegramMessageSender) {
        return new MessageListenerAdapter(telegramMessageSender, "send");
    }
}
