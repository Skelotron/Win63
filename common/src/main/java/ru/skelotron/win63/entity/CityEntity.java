package ru.skelotron.win63.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "city")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CityEntity extends AuditedEntity {

    @Column
    private String externalId;

    @Column
    private String name;

    @Column
    private boolean active;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CityEntity)) return false;
        CityEntity that = (CityEntity) o;
        return externalId.equals(that.externalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(externalId);
    }
}
