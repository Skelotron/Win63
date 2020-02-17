package ru.skelotron.win63.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;
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
}
