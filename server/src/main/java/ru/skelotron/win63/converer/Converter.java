package ru.skelotron.win63.converer;

public interface Converter<R, E> {
    E convertToEntity(R record);
}
