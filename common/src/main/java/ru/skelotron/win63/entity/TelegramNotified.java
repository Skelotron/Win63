package ru.skelotron.win63.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;

@Entity
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@DiscriminatorValue("TELEGRAM")
public class TelegramNotified extends Notified {
    @Column
    private String userName;

    @Column
    private Long userId;

    @Column
    private Long chatId;

    public TelegramNotified() {
        this(null, null, new HashSet<>());
    }

    public TelegramNotified(String userName, String textTemplate, Set<Filter> filters) {
        super(NotificationType.TELEGRAM, filters, textTemplate);
        this.userName = userName;
    }

    @Override
    public String getRecipient() {
        return getUserName();
    }
}
