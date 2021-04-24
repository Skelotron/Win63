package ru.skelotron.win63.entity;

import lombok.*;

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
@DiscriminatorValue("EMAIL")
public class EmailNotified extends Notified {
    @Column
    private String email;

    @Column
    private String subjectTemplate;

    public EmailNotified() {
        this(null, null, null, new HashSet<>());
    }

    public EmailNotified(String email, String subjectTemplate, String textTemplate, Set<Filter> filters) {
        super(NotificationType.EMAIL, filters, textTemplate);
        this.email = email;
        this.subjectTemplate = subjectTemplate;
    }

    @Override
    public String getRecipient() {
        return getEmail();
    }
}
