package ru.skelotron.win63.http_entities.geo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class GetCities implements Serializable {
    private int code;
    private String status;
    private List<GetCity> data;
}
