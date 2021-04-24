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

@Service
public class TelegramMessagePublisher implements NotificationSender {
    public static final String QUEUE_NAME = "TELEGRAM_QUEUE";

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public TelegramMessagePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
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
        rabbitTemplate.convertAndSend("telegram-exchange", "", message);
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
