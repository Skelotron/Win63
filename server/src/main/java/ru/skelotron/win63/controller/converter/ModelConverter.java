package ru.skelotron.win63.controller.converter;

import ru.skelotron.win63.controller.model.AbstractModel;
import ru.skelotron.win63.entity.Entity;

public interface ModelConverter<E extends Entity, M extends AbstractModel> {
    M convertToModel(E entity);

    E convertToEntity(M model);
}
