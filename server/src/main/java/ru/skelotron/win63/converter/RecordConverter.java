package ru.skelotron.win63.converter;

public interface RecordConverter<R, E> {
    E convertToEntity(R record);
}
