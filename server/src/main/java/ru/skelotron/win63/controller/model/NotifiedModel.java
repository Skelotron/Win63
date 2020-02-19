package ru.skelotron.win63.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotifiedModel extends AbstractModel {
    private Long id;
    private String recipient;
    private String subject;
    private String message;
    private List<FilterModel> filters;
}
