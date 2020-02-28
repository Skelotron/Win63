package ru.skelotron.win63.mvc.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public abstract class SynchronizationModel extends AbstractModel {
    private Long id;
    private Date syncDate;
    private long newItemsCount;
    private Boolean manual;
}
