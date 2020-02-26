package ru.skelotron.win63.controller.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public abstract class SynchronizationModel extends AbstractModel {
    private Long id;
    private Date syncDate;
    private int newItemsCount;
    private boolean manual;
}
