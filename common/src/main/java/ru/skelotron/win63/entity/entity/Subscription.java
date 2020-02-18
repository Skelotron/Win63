package ru.skelotron.win63.entity.entity;

import lombok.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "subscription")
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Subscription extends AuditedEntity {

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "subscription_notified",
            joinColumns = @JoinColumn(name = "subscription_id"),
            inverseJoinColumns = @JoinColumn(name = "notified_id")
    )
    private Set<Notified> notifiedEntities = new HashSet<>();
}
