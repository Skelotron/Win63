package ru.skelotron.win63.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skelotron.win63.controller.model.CategoryModel;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Categories implements Serializable {
    private List<CategoryModel> categories;
}
