package ru.skelotron.win63.mvc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionModel extends RepresentationModel<SubscriptionModel> {
    private Long id;
    private CategoryModel category;
    private List<NotifiedModel<?>> notifiedList;
}
