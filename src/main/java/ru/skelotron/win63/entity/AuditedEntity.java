package ru.skelotron.win63.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public abstract class AuditedEntity extends Entity {
    @CreationTimestamp
    @Column
    private Date created;

    @UpdateTimestamp
    @Column
    private Date updated;
}
