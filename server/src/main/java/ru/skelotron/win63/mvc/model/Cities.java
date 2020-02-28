package ru.skelotron.win63.mvc.model;

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
    private List<CityModel> cities;

    @Override
    public List<CityModel> getModels() {
        return cities;
    }

    @Override
    public void setModels(List<CityModel> models) {
        this.cities = models;
    }
}
