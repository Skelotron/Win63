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

    @Column
    private String textTemplate;

    public EmailNotified() {
        this(null, null, null, new HashSet<>());
    }

    public EmailNotified(String email, String subjectTemplate, String textTemplate, Set<Filter> filters) {
        setNotificationType(NotificationType.EMAIL);
        setFilters(filters);
        for (Filter filter : filters) {
            filter.setNotified(this);
        }
        this.email = email;
        this.subjectTemplate = subjectTemplate;
        this.textTemplate = textTemplate;
    }

    @Override
    public String getRecipient() {
        return getEmail();
    }
}
