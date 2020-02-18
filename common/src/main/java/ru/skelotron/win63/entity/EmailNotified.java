package ru.skelotron.win63.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@DiscriminatorValue("EMAIL")
public class EmailNotified extends Notified {
    @Column
    private String email;

    @Column
    private String subjectTemplate;

    @Column
    private String textTemplate;

    public EmailNotified() {
        this(null, null, null);
    }

    public EmailNotified(String email, String subjectTemplate, String textTemplate) {
        setNotificationType(NotificationType.EMAIL);
        this.email = email;
        this.subjectTemplate = subjectTemplate;
        this.textTemplate = textTemplate;
    }

    @Override
    public String getRecipient() {
        return getEmail();
    }
}
