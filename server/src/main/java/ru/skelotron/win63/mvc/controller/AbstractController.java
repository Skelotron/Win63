package ru.skelotron.win63.mvc.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import ru.skelotron.win63.entity.Entity;
import ru.skelotron.win63.mvc.converter.ModelConverter;

public abstract class AbstractController<C extends ModelConverter<E, M> & RepresentationModelAssembler<E, M>, R extends JpaRepository<E, Long>, E extends Entity, M extends RepresentationModel<M>> {
    private final C converter;
    private final R repository;

    public AbstractController(C converter, R repository) {
        this.converter = converter;
        this.repository = repository;
    }

    protected CollectionModel<M> getAllRecordsHolder() {
        return convertToHolder(getRepository().findAll());
    }

    protected CollectionModel<M> getAllRecordsHolder(Pageable pageable) {
        return convertToHolder(getRepository().findAll(pageable).getContent());
    }

    protected CollectionModel<M> convertToHolder(Iterable<E> entities) {
        return getConverter().toCollectionModel(entities)
                .add(WebMvcLinkBuilder.linkTo(getClass()).withSelfRel());
    }

    protected C getConverter() {
        return converter;
    }

    protected R getRepository() {
        return repository;
    }
}
