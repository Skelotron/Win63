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
import ru.skelotron.win63.email_sender.EmailPublisher;
import ru.skelotron.win63.email_sender.EmailSender;

@Configuration
@SuppressWarnings("MethodMayBeStatic")
public class EmailConfig {
    @Bean
    public Queue emailQueue() {
        return new Queue(EmailPublisher.QUEUE_NAME, false);
    }

    @Bean
    public TopicExchange emailTopicExchange() {
        return new TopicExchange("email-exchange");
    }

    @Bean
    public Binding emailBinding(Queue emailQueue, TopicExchange emailTopicExchange) {
        return BindingBuilder.bind(emailQueue).to(emailTopicExchange).with("");
    }

    @Bean
    public SimpleMessageListenerContainer emailContainer(ConnectionFactory connectionFactory, MessageListenerAdapter emailListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(EmailPublisher.QUEUE_NAME);
        container.setMessageListener(emailListenerAdapter);
        return container;
    }

    @Bean
    public MessageListenerAdapter emailListenerAdapter(EmailSender emailSender) {
        return new MessageListenerAdapter(emailSender, "send");
    }
}
