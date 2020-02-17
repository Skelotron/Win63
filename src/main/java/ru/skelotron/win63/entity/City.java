package ru.skelotron.win63.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "city")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class City extends AuditedEntity {

    @Column
    private String externalId;

    @Column
    private String name;

    @Column
    private String timeZone;

    @Column
    private boolean active;
}
