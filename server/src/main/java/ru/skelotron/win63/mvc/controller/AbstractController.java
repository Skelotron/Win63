package ru.skelotron.win63.mvc.controller;

import org.springframework.data.repository.CrudRepository;
import ru.skelotron.win63.entity.Entity;
import ru.skelotron.win63.mvc.converter.ModelConverter;
import ru.skelotron.win63.mvc.model.AbstractModel;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractController<C extends ModelConverter<E, M>, R extends CrudRepository<E, Long>, E extends Entity, M extends AbstractModel> {
    private final C converter;
    private final R repository;

    public AbstractController(C converter, R repository) {
        this.converter = converter;
        this.repository = repository;
    }

    protected List<M> getAllRecords() {
        Iterable<E> entities = getRepository().findAll();

        List<M> models = new ArrayList<>();

        for (E entity : entities) {
            models.add( getConverter().convertToModel(entity) );
        }

        return models;
    }

    protected C getConverter() {
        return converter;
    }

    protected R getRepository() {
        return repository;
    }
}
