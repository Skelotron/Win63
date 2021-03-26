package ru.skelotron.win63.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "category", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "url"}))
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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CategoryEntity that = (CategoryEntity) obj;
        return Objects.equals(name, that.name) &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }
}
