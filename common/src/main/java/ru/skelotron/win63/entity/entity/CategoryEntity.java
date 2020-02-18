package ru.skelotron.win63.entity.entity;

import lombok.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "category")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryEntity that = (CategoryEntity) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }
}
