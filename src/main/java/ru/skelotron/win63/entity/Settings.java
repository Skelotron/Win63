package ru.skelotron.win63.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;


@Entity
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Settings extends AuditedEntity {
    @Column
    private String name;

    @Column
    private String value;

    public Settings(String name, Object value) {
        this.name = name;
        this.value = String.valueOf(value);
    }
}
