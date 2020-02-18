package ru.skelotron.win63.controller.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CategoryModel implements Serializable {
    private String name;
    private String url;

    public CategoryModel(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
