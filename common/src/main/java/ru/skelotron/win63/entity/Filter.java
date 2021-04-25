package ru.skelotron.win63.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@javax.persistence.Entity
@Table(name = "filter")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Filter extends Entity {
    @Enumerated(EnumType.STRING)
    @Column
    private FilterRelationType type;

    @Column
    private String entity;

    @Column
    private String field;

    @Column
    private String value;

    @ManyToOne
    @JoinColumn(name = "notified_id")
    private Notified notified;

    public Filter(FilterRelationType type, String entity, String field, String value) {
        this.type = type;
        this.entity = entity;
        this.field = field;
        this.value = value;
    }
}
