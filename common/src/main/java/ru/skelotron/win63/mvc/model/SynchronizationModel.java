package ru.skelotron.win63.mvc.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@Getter
@Setter
public abstract class SynchronizationModel<T extends RepresentationModel<? extends T>> extends RepresentationModel<T> {
    private Long id;
    private Date syncDate;
    private long newItemsCount;
    private Boolean manual;
}
