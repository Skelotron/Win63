package ru.skelotron.win63.http_entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Sort {
    private String field;
    private String direction;
    private String type;
    private String title;
    private Boolean active;
}
