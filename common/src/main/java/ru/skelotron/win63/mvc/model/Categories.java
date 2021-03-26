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
public class Categories implements ModelListHolder<CategoryModel> {
    private List<CategoryModel> categories;

    @Override
    public List<CategoryModel> getModels() {
        return categories;
    }

    @Override
    public void setModels(List<CategoryModel> models) {
        this.categories = models;
    }
}
