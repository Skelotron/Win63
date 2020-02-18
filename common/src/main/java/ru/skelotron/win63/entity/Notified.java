package ru.skelotron.win63.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Set;

@Entity
@DiscriminatorColumn
@Table(name = "notified")
@Setter
@Getter
@Inheritance
public abstract class Notified extends AuditedEntity {
    @Enumerated(EnumType.STRING)
    @Column
    private NotificationType notificationType;

    @ManyToMany(mappedBy = "notifiedEntities")
    private Set<Subscription> subscriptions;

    @OneToMany(mappedBy = "notified", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Filter> filters;

    public abstract String getRecipient();
}
