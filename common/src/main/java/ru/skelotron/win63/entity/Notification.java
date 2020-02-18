package ru.skelotron.win63.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
@DiscriminatorColumn(name = "type")
@Getter
@Setter
@Table(name = "notification")
public abstract class Notification extends AuditedEntity {
    @Column
    @Enumerated(EnumType.STRING)
    private NotificationType type;
}
