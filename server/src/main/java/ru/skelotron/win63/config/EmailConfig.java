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
import ru.skelotron.win63.email_sender.EmailSender;
import ru.skelotron.win63.email_sender.config.properties.EmailProperties;

@Configuration
public class EmailConfig {

    private final EmailProperties emailProperties;

    public EmailConfig(EmailProperties emailProperties) {
        this.emailProperties = emailProperties;
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(emailProperties.getQueue());
    }

    @Bean
    public TopicExchange emailTopicExchange() {
        return new TopicExchange(emailProperties.getExchange());
    }

    @Bean
    public Binding emailBinding(Queue emailQueue, TopicExchange emailTopicExchange) {
        return BindingBuilder.bind(emailQueue).to(emailTopicExchange).with(emailProperties.getKey());
    }

    @Bean
    public SimpleMessageListenerContainer emailContainer(ConnectionFactory connectionFactory, MessageListenerAdapter emailListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(emailProperties.getQueue());
        container.setMessageListener(emailListenerAdapter);
        return container;
    }

    @Bean
    public MessageListenerAdapter emailListenerAdapter(EmailSender emailSender) {
        return new MessageListenerAdapter(emailSender, "send");
    }
}
