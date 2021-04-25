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
public class Categories implements ModelListHolder<CategoryModel> {
    @JsonProperty("categories")
    private List<CategoryModel> models;

    @Override
    public List<CategoryModel> getModels() {
        return models;
    }

    @Override
    public void setModels(List<CategoryModel> models) {
        this.models = models;
    }
}
