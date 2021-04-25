package ru.skelotron.win63.mvc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Cities implements ModelListHolder<CityModel> {
    @JsonProperty("cities")
    private List<CityModel> models;

    @Override
    public List<CityModel> getModels() {
        return models;
    }

    @Override
    public void setModels(List<CityModel> models) {
        this.models = models;
    }
}
