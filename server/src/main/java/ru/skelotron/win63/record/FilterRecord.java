package ru.skelotron.win63.record;

import lombok.Getter;
import lombok.Setter;
import ru.skelotron.win63.entity.FilterRelationType;

@Getter
@Setter
public class FilterRecord {
    private FilterRelationType type;
    private String entity;
    private String field;
    private String value;
}
