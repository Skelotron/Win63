package ru.skelotron.win63.mvc.converter;

import ru.skelotron.win63.mvc.model.AbstractModel;
import ru.skelotron.win63.entity.Entity;

public interface ModelConverter<E extends Entity, M extends AbstractModel> {
    M convertToModel(E entity);

    E convertToEntity(M model);
}
