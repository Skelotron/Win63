package ru.skelotron.win63.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skelotron.win63.controller.model.SubscriptionModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Subscriptions implements Serializable {
    private List<SubscriptionModel> subscriptions = new ArrayList<>();
}
