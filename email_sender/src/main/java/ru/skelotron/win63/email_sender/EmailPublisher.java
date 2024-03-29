package ru.skelotron.win63.email_sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skelotron.win63.common.MessageProcessor;
import ru.skelotron.win63.common.NotificationSender;
import ru.skelotron.win63.config.RabbitQueueConfig;
import ru.skelotron.win63.entity.EmailNotified;
import ru.skelotron.win63.entity.Item;
import ru.skelotron.win63.entity.NotificationType;
import ru.skelotron.win63.entity.Notified;

import java.util.Collections;

@Service
public class EmailPublisher implements NotificationSender {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitQueueConfig emailProperties;

    @Autowired
    public EmailPublisher(RabbitTemplate rabbitTemplate, RabbitQueueConfig emailProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.emailProperties = emailProperties;
    }

    @Override
    public void send(Notified notified, Iterable<Item> items) {
        if (notified instanceof EmailNotified) {
            for (Item item : items) {
                Email email = convertToEmail((EmailNotified) notified, item);
                send(email);
            }
        }
    }

    public void send(Email email) {
        rabbitTemplate.convertAndSend(emailProperties.getExchange(), emailProperties.getKey(), email);
    }

    private Email convertToEmail(EmailNotified notified, Item item) {
        Email email = new Email();
        email.setTo( Collections.singletonList( notified.getRecipient() ) );
        email.setSubject( new MessageProcessor(notified.getSubjectTemplate()).process(item) );
        email.setMessage( new MessageProcessor(notified.getTextTemplate()).process(item) );
        return email;
    }

    @Override
    public NotificationType getType() {
        return NotificationType.EMAIL;
    }
}
