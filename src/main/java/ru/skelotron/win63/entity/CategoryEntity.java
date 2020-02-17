package ru.skelotron.win63.entity;

import lombok.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
@EqualsAndHashCode(callSuper = true, exclude = "parentCategory")
@Getter
@Setter
@ToString(exclude = "parentCategory")
@NoArgsConstructor
public class CategoryEntity extends AuditedEntity {

    @Column
    private String name;

    @Column
    private String url;

    @Column
    private String externalId;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parentCategory", cascade = CascadeType.ALL)
    private Set<CategoryEntity> subCategories = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private CategoryEntity parentCategory;

    public CategoryEntity(String name, String url, String externalId) {
        this.name = name;
        this.url = url;
        this.externalId = externalId;
    }

    public CategoryEntity(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
