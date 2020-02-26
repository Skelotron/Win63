package ru.skelotron.win63.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import java.util.Date;

@MappedSuperclass
@DiscriminatorColumn(name = "type")
@Getter
@Setter
@Table(name = "synchronization")
public abstract class SynchronizationEntity extends AuditedEntity {
    private String type;

    @Column
    private boolean manual;

    @Column
    private Date syncDate;

    @Column
    private int newEntitiesCount;
}
