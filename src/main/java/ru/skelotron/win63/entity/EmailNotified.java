package ru.skelotron.win63.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("EMAIL")
public class EmailNotified extends Notified {
    @Column
    private String email;

    @Column
    private String subjectTemplate;

    @Column
    private String textTemplate;
}
