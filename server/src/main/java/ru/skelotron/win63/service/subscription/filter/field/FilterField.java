package ru.skelotron.win63.service.subscription.filter.field;

import ru.skelotron.win63.entity.Entity;
import ru.skelotron.win63.entity.FilterRelationType;
import ru.skelotron.win63.service.subscription.filter.serial.Deserializer;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public interface FilterField<T extends Entity> {
    List<FilterRelationType> getSupportedRelations();
    Supplier<Deserializer<?>> getDeserializer();
    Function<T, Object> getFieldRetriever();
}
