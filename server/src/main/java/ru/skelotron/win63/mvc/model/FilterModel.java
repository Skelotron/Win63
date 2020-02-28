package ru.skelotron.win63.mvc.model;

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
    private Object value;
}
