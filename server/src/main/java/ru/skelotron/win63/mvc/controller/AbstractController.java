package ru.skelotron.win63.mvc.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.skelotron.win63.entity.Entity;
import ru.skelotron.win63.mvc.converter.ModelConverter;
import ru.skelotron.win63.mvc.model.AbstractModel;
import ru.skelotron.win63.mvc.model.ModelListHolder;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractController<C extends ModelConverter<E, M>, R extends JpaRepository<E, Long>, E extends Entity, M extends AbstractModel> {
    private final C converter;
    private final R repository;

    public AbstractController(C converter, R repository) {
        this.converter = converter;
        this.repository = repository;
    }

    protected ModelListHolder<M> getAllRecordsHolder() {
        return convertToHolder(getRepository().findAll());
    }

    protected ModelListHolder<M> getAllRecordsHolder(Pageable pageable) {
        return convertToHolder(getRepository().findAll(pageable).getContent());
    }

    protected ModelListHolder<M> convertToHolder(Iterable<E> entities) {
        List<M> modelList = convert(entities);
        ModelListHolder<M> modelListHolder = createModelListHolder();
        modelListHolder.setModels(modelList);
        return modelListHolder;
    }

    protected List<M> convert(Iterable<E> entities) {
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

    protected abstract ModelListHolder<M> createModelListHolder();
}
