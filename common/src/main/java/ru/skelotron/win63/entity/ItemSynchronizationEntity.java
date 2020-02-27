package ru.skelotron.win63.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@DiscriminatorValue("Item")
@Getter
@Setter
@Entity
public class ItemSynchronizationEntity extends SynchronizationEntity {

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    public ItemSynchronizationEntity() {
        setType("Item");
    }
}
