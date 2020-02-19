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
public class SubscriptionModel extends AbstractModel {
    private Long id;
    private CategoryModel category;
    private List<NotifiedModel> notifiedList;
}
