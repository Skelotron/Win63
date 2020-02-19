package ru.skelotron.win63.controller.converter;

public interface ModelConverter<E, M> {
    M convertToModel(E entity);

    E convertToEntity(M model);
}
