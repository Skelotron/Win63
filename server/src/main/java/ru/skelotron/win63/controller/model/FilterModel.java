package ru.skelotron.win63.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skelotron.win63.entity.FilterRelationType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterModel extends AbstractModel {
    private Long id;
    private String field;
    private FilterRelationType relation;
    private String value;
}
