package ru.skelotron.win63.exception;

public class EntityNotFoundException extends IllegalArgumentException {

    public EntityNotFoundException(Class<?> entityCls, Long id) {
        super(String.format("Can't find %s with id = %d", entityCls.getSimpleName(), id));
    }
}
