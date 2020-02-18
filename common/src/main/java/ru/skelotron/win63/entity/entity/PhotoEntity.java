package ru.skelotron.win63.entity.entity;


import lombok.*;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "photo")
@EqualsAndHashCode(callSuper = true, exclude = {"content", "item"})
@Getter
@Setter
@ToString(exclude = {"content", "item"})
@NoArgsConstructor
public class PhotoEntity extends AuditedEntity {
    @Column
    @Lob
    @Basic(fetch = FetchType.EAGER)
    private byte[] content;

    @Column
    private String description;

    @Column
    private String url;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    public PhotoEntity(byte[] content, String description, String url) {
        this.content = content;
        this.description = description;
        this.url = url;
    }
}
