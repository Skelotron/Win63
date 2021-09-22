package ru.skelotron.win63.mvc.converter;

import org.springframework.hateoas.RepresentationModel;
import ru.skelotron.win63.entity.Entity;

public interface ModelConverter<E extends Entity, M extends RepresentationModel> {
    E convertToEntity(M model);
}
