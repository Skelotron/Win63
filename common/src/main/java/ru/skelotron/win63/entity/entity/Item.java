package ru.skelotron.win63.entity.entity;

import lombok.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Item extends AuditedEntity {

    @Column
    private String url;

    @Column
    private String title;

    @Column
    private BigDecimal amount;

    @Column
    private Date insertTime;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PhotoEntity> photos;

    public Item(String url, String title, BigDecimal amount, Date insertTime, CategoryEntity category, Set<PhotoEntity> photos) {
        this.url = url;
        this.title = title;
        this.amount = amount;
        this.insertTime = insertTime;
        this.category = category;
        this.photos = photos;
        for (PhotoEntity photo : photos) {
            photo.setItem(this);
        }
    }
}
