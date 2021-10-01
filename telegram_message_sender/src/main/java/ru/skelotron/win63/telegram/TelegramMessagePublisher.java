package ru.skelotron.win63.telegram;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skelotron.win63.common.MessageProcessor;
import ru.skelotron.win63.common.NotificationSender;
import ru.skelotron.win63.entity.Item;
import ru.skelotron.win63.entity.NotificationType;
import ru.skelotron.win63.entity.Notified;
import ru.skelotron.win63.entity.TelegramNotified;
import ru.skelotron.win63.telegram.config.properties.TelegramProperties;

@Service
public class TelegramMessagePublisher implements NotificationSender {
    private final RabbitTemplate rabbitTemplate;
    private final TelegramProperties telegramProperties;

    @Autowired
    public TelegramMessagePublisher(RabbitTemplate rabbitTemplate, TelegramProperties telegramProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.telegramProperties = telegramProperties;
    }

    @Override
    public void send(Notified notified, Iterable<Item> items) {
        if (notified instanceof TelegramNotified) {
            TelegramNotified telegramNotified = (TelegramNotified) notified;
            if (valid(telegramNotified)) {
                for (Item item : items) {
                    TelegramMessage message = convertToMessage(telegramNotified, item);
                    send(message);
                }
            }
        }
    }

    private boolean valid(TelegramNotified telegramNotified) {
        return telegramNotified.getChatId() != null;
    }

    public void send(TelegramMessage message) {
        rabbitTemplate.convertAndSend(telegramProperties.getExchange(), telegramProperties.getKey(), message);
    }

    private TelegramMessage convertToMessage(TelegramNotified notified, Item item) {
        TelegramMessage email = new TelegramMessage();
        email.setChatId( notified.getChatId() );
        email.setText( new MessageProcessor(notified.getTextTemplate()).process(item) );
        return email;
    }

    @Override
    public NotificationType getType() {
        return NotificationType.TELEGRAM;
    }
}
